package mall.controller;

import jakarta.validation.Valid;
import mall.bean.Furn;
import mall.service.FurnService;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public ResponseEntity<String> uploadFurnImg(@RequestParam("upload") MultipartFile file) {
        String imgPath = contextPath + "img/" + file.getOriginalFilename();
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

    @DeleteMapping("/deleteFurn")
    public ResponseEntity<String> deleteFurn(@RequestParam("id") Integer id) {
        if (furnService.deleteById(id)) {
            return new ResponseEntity<>("successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getFurnImg")
    public ResponseEntity<Resource> getFurnImg(@RequestParam("url") String url) throws IOException {
        FileSystemResource fileSystemResource = new FileSystemResource(url);
        HttpHeaders headers = new HttpHeaders();
        String filename = fileSystemResource.getFilename();
        String encodedFilename = URLEncoder.encode(Objects.requireNonNull(filename), StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename*=UTF-8''" + encodedFilename);

        headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(Paths.get(fileSystemResource.getURI())));
        return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }


    @PutMapping("/updateFurn")
    public ResponseEntity<String> updateFurn(@Valid @RequestBody Furn furn) {
        if (furnService.updateFurn(furn)) {
            return new ResponseEntity<>("successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
