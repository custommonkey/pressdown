package org.custommonkey.pressdown;

import static org.custommonkey.pressdown.InputSourceUtils.readChars;

import org.pegdown.PegDownProcessor;
import org.pegdown.ast.*;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

class SAXEventVisitor implements XMLReader, Visitor {

    private static final AttributesImpl NONE = new AttributesImpl();

    private ContentHandler handler;
    private final PegDownProcessor pegDownProcessor = new PegDownProcessor();

    @Override
    public void visit(final TextNode node) {
        try {
            final char[] charArray = node.getText().toCharArray();
            handler.characters(charArray, 0, charArray.length);
        } catch (final SAXException e) {
            e.printStackTrace();
        }
    }

    private void printTag(final SuperNode node, final String tag) {
        try {
            handler.startElement("", tag, tag, NONE);
            visitChildren(node);
            handler.endElement("", tag, tag);
        } catch (final SAXException e) {
            e.printStackTrace();
        }
    }

    private void visitChildren(final SuperNode node) {
        for (final Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    @Override
    public void visit(final HeaderNode node) {
        printTag(node, "h" + node.getLevel());
    }

    @Override
    public void visit(final CodeNode node) {
        System.out.println(node);
    }

    @Override
    public ContentHandler getContentHandler() {
        return null;
    }

    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public boolean getFeature(final String name) {
        return false;
    }

    @Override
    public Object getProperty(final String name) {
        return null;
    }

    @Override
    public void parse(final InputSource input) {
        try {
            handler.startDocument();
            start("html");
            start("body");
            visit(pegDownProcessor.parseMarkdown(readChars(input)));
            end("body");
            end("html");
            handler.endDocument();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void end(final String tag) throws SAXException {
        handler.endElement("", tag, tag);
    }

    private void start(final String tag) throws SAXException {
        handler.startElement("", tag, tag, NONE);
    }

    @Override
    public void parse(final String systemId) {}

    @Override
    public void setContentHandler(final ContentHandler handler) {
        this.handler = handler;
    }

    @Override
    public void setDTDHandler(final DTDHandler handler) {}

    @Override
    public void setEntityResolver(final EntityResolver resolver) {}

    @Override
    public void setErrorHandler(final ErrorHandler handler) {}

    @Override
    public void setFeature(final String name, final boolean value) {}

    @Override
    public void setProperty(final String name, final Object value) {}

    @Override
    public void visit(final AbbreviationNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final AutoLinkNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final BlockQuoteNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final BulletListNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final DefinitionListNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final DefinitionNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final DefinitionTermNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final EmphNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ExpImageNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ExpLinkNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final HtmlBlockNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final InlineHtmlNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ListItemNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final MailLinkNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final OrderedListNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ParaNode para) {
        printTag(para, "p");
    }

    @Override
    public void visit(final QuotedNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ReferenceNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final RefImageNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final RefLinkNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final RootNode root) {
        visitChildren(root);
    }

    @Override
    public void visit(final SimpleNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final SpecialTextNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final StrongNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableBodyNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableCaptionNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableCellNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableColumnNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableHeaderNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableRowNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final VerbatimNode verbatim) {
        try {
            start("pre");
            start("code");
            visit((TextNode) verbatim);
            end("code");
            end("pre");
        } catch (final SAXException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visit(final WikiLinkNode arg0) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final SuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final Node arg0) {
        throw new UnsupportedOperationException();
    }

}
