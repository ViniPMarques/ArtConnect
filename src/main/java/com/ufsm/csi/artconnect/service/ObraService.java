package com.ufsm.csi.artconnect.service;

import com.ufsm.csi.artconnect.dto.UsuarioDto;
import com.ufsm.csi.artconnect.model.Obra;
import com.ufsm.csi.artconnect.model.Usuario;
import com.ufsm.csi.artconnect.repository.ObraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ObraService {

    private static final Path ROOT_LOACATION = Paths.get("./uploads");

    @Autowired
    private ObraRepository obraRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveObra(MultipartFile file, UsuarioDto u) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Failed to store empty file.");
        }
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(), ROOT_LOACATION.resolve(fileName));

        Obra obra = new Obra();
        obra.setFileName(fileName);
        obra.setFilePath(ROOT_LOACATION.resolve(fileName).toString());
        obra.setIdartista(u.getIdusuario());
        obraRepository.save(obra);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteObra(Long id, UsuarioDto u) throws IOException {
        Optional<Obra> obra = obraRepository.findByIdartistaAndIdObra(u.getIdusuario(), id);

        if(!obra.isPresent()){
            throw new RuntimeException("Obra não pertence ao artista");
        }

        Files.delete(Paths.get(obra.get().getFilePath()));
        obraRepository.delete(obra.get());
    }

    public List<Obra> findObrasByArtistId(Long artistId) {
        return obraRepository.findByIdartista(artistId);
    }

    public Path getObraPath(String filename) {
        return ROOT_LOACATION.resolve(filename);
    }
}
