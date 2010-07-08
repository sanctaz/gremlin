package com.tinkerpop.gremlin.compiler.functions.g.graph;

import com.tinkerpop.blueprints.pgm.Element;
import com.tinkerpop.blueprints.pgm.Graph;
import com.tinkerpop.gremlin.compiler.Atom;
import com.tinkerpop.gremlin.compiler.functions.AbstractFunction;
import com.tinkerpop.gremlin.compiler.functions.FunctionHelper;
import com.tinkerpop.gremlin.compiler.operations.Operation;

import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class KeyFunction extends AbstractFunction<Iterable<Element>> {

    private static final String FUNCTION_NAME = "key";

    public Atom<Iterable<Element>> compute(final List<Operation> parameters) throws RuntimeException {

        final int size = parameters.size();
        if (size != 2 && size != 3)
            throw new RuntimeException(this.createUnsupportedArgumentMessage());

        final Graph graph = FunctionHelper.getGraph(parameters, 0);
        final String key;
        final Object value;

        if (size == 3) {
            key = (String) parameters.get(1).compute().getValue();
            value = parameters.get(2).compute().getValue();
        } else {
            key = (String) parameters.get(0).compute().getValue();
            value = parameters.get(1).compute().getValue();
        }

        return new Atom<Iterable<Element>>(graph.getIndex().get(key, value));
    }

    public String getFunctionName() {
        return FUNCTION_NAME;
    }

}