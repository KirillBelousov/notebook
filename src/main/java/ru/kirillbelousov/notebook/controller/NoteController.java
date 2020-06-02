package ru.kirillbelousov.notebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kirillbelousov.notebook.entity.Note;
import ru.kirillbelousov.notebook.service.NoteService;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public String getList(Model model) {
        List<Note> list = noteService.getAll();
        model.addAttribute("notes", list );
        return "notesList";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Long id, Model model) {
        try {
            Note note = noteService.getById(id);
            model.addAttribute("note", note );
            return "oneNote";
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        }
    }

    @GetMapping("/new")
     public String showCreationForm() {
        return "createNewNote";
    }

    @PostMapping("/new")
    public String saveNew(Note newNote) {
        noteService.save(newNote);
        return "redirect:" + newNote.getId();
    }

    @PostMapping("/edit/{id}")
    public String getForEdit(@PathVariable Long id, Model model) {
        try {
            Note note = noteService.getById(id);
            model.addAttribute("note", note );
            return "editNote";
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        }
    }

    @PostMapping("/edit")
    public String edit(Note note) {
        noteService.update(note);
        return "redirect:" + note.getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        noteService.deleteById(id);
        return "redirect:/notes";
    }

    @GetMapping("/{id}/history")
    public String showHistory(@PathVariable Long id, Model model) {
        try {
            List<Note> list = noteService.getHistoryOfNonDeletedById(id);
            model.addAttribute("notes", list);
            model.addAttribute("noteId", id);
            return "historyList";
        }
        catch (NoSuchElementException e) {
            return "noteNotFound";
        }
    }
}
