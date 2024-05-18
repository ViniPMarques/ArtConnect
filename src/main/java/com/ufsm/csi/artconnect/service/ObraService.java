package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ObraService {

    private final Path rootLocation = Paths.get("uploads");

    @Autowired
    private ObraRepository obraRepository;

    public void saveObra(MultipartFile file, Long artistId) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }
        String fileName = file.getOriginalFilename();
        Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));

        Obra obra = new Obra();
        obra.setFileName(fileName);
        obra.setFilePath(this.rootLocation.resolve(fileName).toString());
        obra.setIdartista(artistId);
        obraRepository.save(obra);
    }

    public List<Obra> findObrasByArtistId(Long artistId) {
        return obraRepository.findByIdartista(artistId);
    }

    public Path getObraPath(String filename) {
        return rootLocation.resolve(filename);
    }
}
