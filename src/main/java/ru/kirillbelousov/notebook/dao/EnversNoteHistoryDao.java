package ru.kirillbelousov.notebook.dao;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQueryCreator;
import org.springframework.stereotype.Repository;
import ru.kirillbelousov.notebook.entity.Note;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Repository
public class EnversNoteHistoryDao {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Note> getHistoryOfNonDeletedById(long id) throws NoSuchElementException {
        List<Object[]> all = getAllById(id);
        if( all.isEmpty() || isDeleted( getLast(all) ) ){
            throw new NoSuchElementException();
        }
        else {
            return getEntityList(all);
        }
    }

    private List<Object[]> getAllById(long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQueryCreator query = reader.createQuery();

        List<Object[]> resultList = query
                .forRevisionsOfEntity(ru.kirillbelousov.notebook.entity.Note.class, false, true)
                .add(AuditEntity.id().eq(id))
                .getResultList();

        return resultList;
    }

    private List<Note> getEntityList(List<Object[]> enversObjectList){
        return enversObjectList
                .stream()
                .map(el -> (Note) el[0])
                .collect(Collectors.toList());
    }

    private boolean isDeleted(Object[] enversObject){
        return enversObject[2] == RevisionType.DEL;
    }

    private Object[] getLast(List<Object[]> enversObjectList) {
        return enversObjectList.get(enversObjectList.size() - 1);
    }
}
