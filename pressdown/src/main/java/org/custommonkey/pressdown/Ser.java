package org.custommonkey.pressdown;

import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;
import org.pegdown.ToHtmlSerializer;
import org.pegdown.ast.HeaderNode;
import org.pegdown.ast.Node;
import org.pegdown.ast.SuperNode;
import org.pegdown.ast.TextNode;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

class Ser extends ToHtmlSerializer implements XMLReader {
	private static final AttributesImpl NONE = new AttributesImpl();

	Ser() {
		super(new LinkRenderer());
	}

	@Override
	public void visit(final TextNode node) {
		try {
			final char[] charArray = node.getText().toCharArray();
			handler.characters(charArray, 0, charArray.length);
		} catch (final SAXException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void printTag(final SuperNode node, final String tag) {
		try {
			handler.startElement("", tag, tag, NONE);
			visitChildren(node);
			handler.endElement("", tag, tag);
		} catch (final SAXException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void visitChildren(final SuperNode node) {
		for (final Node child : node.getChildren()) {
			child.accept(this);
		}
	}

	@Override
	public void visit(final HeaderNode node) {
		printTag(node, "h" + node.getLevel());
	}

	@Override
	protected void printIndentedTag(final SuperNode node, final String tag) {
		// printer.println().print('<').print(tag).print('>').indent(+2);
		// visitChildren(node);
		// printer.indent(-2).println().print('<').print('/').print(tag).print('>');
	}

	@Override
	protected void printImageTag(final SuperNode imageNode, final String url) {
		// printer.print("<img src=\"").print(url).print("\"  alt=\"")
		// .printEncoded(printChildrenToString(imageNode)).print("\"/>");
	}

	@Override
	protected void printLink(final LinkRenderer.Rendering rendering) {
		// printer.print('<').print('a');
		// printAttribute("href", rendering.href);
		// for (LinkRenderer.Attribute attr : rendering.attributes) {
		// printAttribute(attr.name, attr.value);
		// }
		// printer.print('>').print(rendering.text).print("</a>");
	}

	ContentHandler handler;
    private final PegDownProcessor pegDownProcessor = new PegDownProcessor();

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
			visit(pegDownProcessor.parseMarkdown(App.readChars(input)));
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
    public void parse(final String systemId) {
	}

	@Override
    public void setContentHandler(final ContentHandler handler) {
		this.handler = handler;
	}

	@Override
    public void setDTDHandler(final DTDHandler handler) {
	}

	@Override
    public void setEntityResolver(final EntityResolver resolver) {
	}

	@Override
    public void setErrorHandler(final ErrorHandler handler) {
	}

	@Override
    public void setFeature(final String name, final boolean value) {
	}

	@Override
    public void setProperty(final String name, final Object value) {
	}

}