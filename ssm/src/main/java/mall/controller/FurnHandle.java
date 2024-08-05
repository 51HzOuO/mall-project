package mall.controller;

import jakarta.validation.Valid;
import mall.bean.Furn;
import mall.service.FurnService;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FurnHandle {
    private final FurnService furnService;

    private final String contextPath;

    public FurnHandle(FurnService furnService, String contextPath) {
        this.furnService = furnService;
        this.contextPath = contextPath;
    }

    @PostMapping("/addFurn")
    public ResponseEntity<String> addFurn(@Valid @RequestBody Furn furn) {
        if (furnService.save(furn)) {
            return new ResponseEntity<>("successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/uploadFurnImg")
    public ResponseEntity<String> uploadFurnImg(@RequestParam("upload") MultipartFile file) throws IOException {
        String imgPath = contextPath + "/img/" + file.getOriginalFilename();
        try {
            file.transferTo(new File(imgPath));
            return new ResponseEntity<>(imgPath, HttpStatus.OK);
        } catch (IOException | IllegalStateException e) {
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllFurn")
    public ResponseEntity<List<Furn>> getAllFurn() {
        return new ResponseEntity<>(furnService.getAllFurn(), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(@NotNull MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
