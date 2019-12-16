package com.smbc.sg.epix.workflow.controller.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smbc.sg.epix.workflow.controller.WorkflowController;
import com.smbc.sg.epix.workflow.dto.WorkflowDTO;
import com.smbc.sg.epix.workflow.service.WorkflowService;

@RestController
public class WorkflowControllerImpl implements WorkflowController{
	private static final Logger logger = LoggerFactory.getLogger(WorkflowControllerImpl.class);

    @Autowired
    private WorkflowService wfService;

    // Get Workflow records
    @Override
    public ResponseEntity<List<WorkflowDTO>> getWorkflowList(@RequestParam String filter) {

    	logger.info("Retrieve Workflow list.");
        List<WorkflowDTO> wfList = wfService.getWorkflowList(filter);
        
        return new ResponseEntity<List<WorkflowDTO>>(wfList, HttpStatus.OK);
    }

    // Save
//    @PostMapping("/books")
//    //return 201 instead of 200
//    @ResponseStatus(HttpStatus.CREATED)
//    Workflow newBook(@RequestBody Workflow newBook) {
//        return repository.save(newBook);
//    }
//
//    // Find
//    @GetMapping("/books/{id}")
//    Workflow findOne(@PathVariable Long id) {
//        return repository.findById(id)
//                .orElseThrow(() -> new BookNotFoundException(id));
//    }
//
//    // Save or update
//    @PutMapping("/books/{id}")
//    Workflow saveOrUpdate(@RequestBody Workflow newBook, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(x -> {
//                    x.setName(newBook.getName());
//                    x.setAuthor(newBook.getAuthor());
//                    x.setPrice(newBook.getPrice());
//                    return repository.save(x);
//                })
//                .orElseGet(() -> {
//                    newBook.setId(id);
//                    return repository.save(newBook);
//                });
//    }
//
//    // update author only
//    @PatchMapping("/books/{id}")
//    Workflow patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(x -> {
//
//                    String author = update.get("author");
//                    if (!StringUtils.isEmpty(author)) {
//                        x.setAuthor(author);
//
//                        // better create a custom method to update a value = :newValue where id = :id
//                        return repository.save(x);
//                    } else {
//                        throw new BookUnSupportedFieldPatchException(update.keySet());
//                    }
//
//                })
//                .orElseGet(() -> {
//                    throw new BookNotFoundException(id);
//                });
//
//    }

//    @DeleteMapping("/books/{id}")
//    void deleteBook(@PathVariable Long id) {
//        repository.deleteById(id);
//    }

}
