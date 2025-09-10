package com.unise.webapp.storage.serializer;

import com.unise.webapp.exception.StorageException;
import com.unise.webapp.model.*;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void doWrite(Resume r, OutputStream os) throws Exception {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());
            Map<ContactType, String> contacts = r.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            writeCollection(dos, r.getSections().entrySet(), entry -> {
                SectionType type = entry.getKey();
                Section section = entry.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeCollection(dos, ((ListSection) section).getItems(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeCollection(dos, ((CompanySection) section).getCompany(), company -> {
                            dos.writeUTF(company.getOrganization().getName());
                            dos.writeUTF(company.getOrganization().getWebsite());
                            writeCollection(dos, company.getPeriods(), period -> {
                                dos.writeUTF(period.getDescription());
                                writeYearsMonth(dos, period.getStartDate());
                                writeYearsMonth(dos, period.getEndDate());
                            });
                        });
                        break;
                }
            });
        }
    }

    private void writeYearsMonth(DataOutputStream dos, YearMonth id) throws Exception {
        dos.writeInt(id.getYear());
        dos.writeInt(id.getMonth().getValue());
    }

    private YearMonth readYearsMonth(DataInputStream dis) throws Exception {
        return YearMonth.of(dis.readInt(), dis.readInt());
    }

    @Override
    public Resume doRead(InputStream is) throws StorageException, IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readItems(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readItems(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Section readSection(DataInputStream dis, SectionType sectionType) throws Exception {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ListSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new CompanySection(
                        readList(dis, () -> new Company(
                                new Organization(dis.readUTF(), dis.readUTF()), readList(dis, () -> new Period(
                                dis.readUTF(), readYearsMonth(dis), readYearsMonth(dis)
                        ))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readList(DataInputStream dis, ElementReader<T> reader) throws Exception {
        int size = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(reader.read());
        }
        return list;
    }

    private interface ElementProcessor {
        void process() throws Exception;
    }

    private interface ElementReader<T> {
        T read() throws Exception;
    }

    private interface ElementWriter<T> {
        void write(T t) throws Exception;
    }

    private void readItems(DataInputStream dis, ElementProcessor processor) throws Exception {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, ElementWriter<T> writer) throws Exception {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writer.write(item);
        }
    }
}





















