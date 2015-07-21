package com.incquerylabs.uml.ralf.api;

import java.util.Map;

import org.eclipse.uml2.uml.OpaqueBehavior;

import com.incquerylabs.uml.ralf.api.impl.ParsingResults;
import com.incquerylabs.uml.ralf.api.impl.SnippetCompilerException;

import snippetTemplate.Snippet;

public interface IReducedAlfGenerator {
    /**
     * Returns tha map that contains C++ snippets and their corresponding rALF fragments.
     * @return
     */
    public Map<Snippet,String> getSnippetMap();
    
    /**
     * Creates a C++ snippet based on the defined rALF code using the provided rALF parser.
     * @param behavior String containing the rALF code
     * @param parser Parser used for parsing the rALF code
     * @return
     */
    public Snippet createSnippet(String behavior, IReducedAlfParser parser) throws SnippetCompilerException;
    
    /**
     * Creates a C++ snippet based on the the rALF code, which is contained by the specified opaque behavior 
     * using the provided rALF parser.
     * @param behavior Opaque behavior containing the rALF code
     * @param parser Parser used for parsing the rALF code
     * @return
     */
    public Snippet createSnippet(OpaqueBehavior behavior, IReducedAlfParser parser) throws SnippetCompilerException;
    
    /**
     * Creates a C++ snippet based on a given rALF AST.
     * @param rootElement
     * @return
     */
    public Snippet createSnippet(ParsingResults result) throws SnippetCompilerException;
}
