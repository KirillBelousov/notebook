package ru.kirillbelousov.notebook;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQueryCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/{id}")
    public String showHistory(@PathVariable Long id, Model model) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQueryCreator query = reader.createQuery();

        List<Object[]> resultList = query
                                    .forRevisionsOfEntity(Note.class, false, true)
                                    .add(AuditEntity.id().eq(id))
                                    .getResultList();

        try {
            if ( resultList.isEmpty() ){
                throw new NoSuchElementException();
            }
            else {
                Object[] lastObject = resultList.get(resultList.size() - 1);
                boolean isDEL = (lastObject[2] == RevisionType.DEL);
                if (isDEL) {
                    throw new NoSuchElementException();
                }
            }
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        };

        List<Note> notesList = resultList
                               .stream()
                               .map(el -> (Note) el[0])
                               .collect(Collectors.toList());

        model.addAttribute("notes", notesList);
        model.addAttribute("noteId", id);
        return "historyList";
    }
}
