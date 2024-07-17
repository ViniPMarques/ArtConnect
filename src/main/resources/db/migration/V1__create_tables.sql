CREATE TABLE Usuario(
                        idusuario SERIAL PRIMARY KEY,
                        nomeUsuario VARCHAR(50) NOT NULL,
                        emailUsuario VARCHAR(50) NOT NULL,
                        profilePicture VARCHAR(200),
                        description VARCHAR(200),
                        senhaUsuario VARCHAR(255) NOT NULL,
                        tipoUsuario INT,
                        ativo BOOL DEFAULT 'true',
                        UNIQUE(emailUsuario)
);

CREATE TABLE Obra(
                     idobra SERIAL PRIMARY KEY,
                     fileName VARCHAR(100) NOT NULL,
                     filePath VARCHAR(100) NOT NULL,
                     idartista INT,
                     FOREIGN KEY (idartista) REFERENCES Usuario(idusuario)
);

CREATE TABLE Comissao(
                         idcomissao SERIAL PRIMARY KEY,
                         descricao VARCHAR(255),
                         valor DECIMAL(10,2),
                         idartista INT,
                         FOREIGN KEY (idartista) REFERENCES Usuario(idusuario)
);

CREATE TABLE Pedido(
                       idpedido SERIAL PRIMARY KEY,
                       idartista INT,
                       idcliente INT,
                       idcomissao INT,
                       dataComissao DATE,
                       dataFinalizacao DATE,
                       descricao VARCHAR(255),
                       valorComissao DECIMAL(10,2),
                       FOREIGN KEY (idartista) REFERENCES Usuario(idusuario),
                       FOREIGN KEY (idcliente) REFERENCES Usuario(idusuario),
                       FOREIGN KEY (idcomissao) REFERENCES Comissao(idcomissao)
);
