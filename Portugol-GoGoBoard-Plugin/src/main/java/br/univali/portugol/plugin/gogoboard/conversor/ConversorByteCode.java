package br.univali.portugol.plugin.gogoboard.conversor;

import br.univali.ps.nucleo.Configuracoes;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

/**
 * Classe responsável por converter o código Logo em bytecode para ser possivel
 * enviar à GoGo Board.
 *
 * O Conversor utiliza um compilador escrito em Python por Arnan (Roger)
 * Sipiatkiat, sob a seguinte licença:
 *
 * 
 * Tinker Logo Compiler - A lexical analyzer and parser for a
 * Logo language for Robotics
 *
 * Copyright (C) 2014 Chiang Mai University
 *  Contact   Arnan (Roger) Sipiatkiat [arnans@gmail.com]
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * @author Ailton Cardoso Jr
 * @version 1.0
 */
public class ConversorByteCode {

    /**
     * Método para converter o código logo passado por parametro em byte code.
     * @param logo Código Logo para a compilação.
     * @return Bytecode resultante da compilação.
     */
    public byte[] converterLogoParaByteCode(String logo) {
        byte[] byteCode = null;
        File caminho = new File(new File(Configuracoes.getInstancia().getDiretorioTemporario(), "pluginPythonDependence"), "logoc.py");
        
        
        // Monta a linha de comando com o arquivo e o argumento
        CommandLine cmdLine = new CommandLine("python");
        cmdLine.addArgument(caminho.getAbsolutePath());
        cmdLine.addArgument(logo);
        // Cria o Executor e o StreamHandler para capturar a saida e colocar no streamSaida
        ByteArrayOutputStream streamSaida = new ByteArrayOutputStream();
        DefaultExecutor executor = new DefaultExecutor();
        PumpStreamHandler streamHandler = new PumpStreamHandler(streamSaida);
        executor.setStreamHandler(streamHandler);
        try {
            executor.execute(cmdLine);
            System.err.println("SAIDA COMPILADOR:\n\n" + streamSaida.toString());
            byteCode = montarArrayBytecode(streamSaida.toString());
        } catch (IOException ex) {
            System.err.println("Erro ao compilar o bytecode " + streamSaida.toString());
            Logger.getLogger(ConversorByteCode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return byteCode;
    }

    /**
     * Método privado para montar um array de bytes contendo o bytecode
     * resultante da compilação.
     */
    private static byte[] montarArrayBytecode(String outputStream) {
        // Quebra a String após o 'Raw byte code'ontarArrayBytecode(ConversorByteCode.java:86
        Pattern pattern = Pattern.compile("([0-9]+)");
        Matcher matcher = pattern.matcher(outputStream);
        
        List<String> arrayString = new ArrayList<>();
        // Quebra o raw byte code por virgula e armazena num array de String
        while (matcher.find())
        {
            String theByte = matcher.group();
            arrayString.add(theByte);
        }

        byte[] bytecode = new byte[arrayString.size() - 1];

        // Transforma os valores em bytes e armazena no array de bytes, ignorando o ultimo valor (vazio " ")
        for (int i = 0; i < arrayString.size() - 1; i++) {
            bytecode[i] = (byte) (Integer.parseInt(arrayString.get(i)) & 255);
        }
        return bytecode;
    }    
    
}
