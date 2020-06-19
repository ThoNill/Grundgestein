package tho.nill.grundgestein.ST;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.stringtemplate.v4.STGroupFile;

public class STDatei<M> extends STTemplate<M> {
	static Logger logger = Logger.getLogger(STDatei.class.getSimpleName());
	private Writer writer;
	private STGroupFile group;

	public STDatei(Charset charset,String vorlageDatei) {
		super(charset);
		this.group = createGroupFile(vorlageDatei);
		register(group);
	}

	protected void erzeugeAusgabe(M vorlageModell,String ausgabeVerzeichnis) throws IOException  {
		erzeugeAusgabeAusVorlageModell(group, ausgabeVerzeichnis, vorlageModell);
	}

	
	protected void ausgabeAktion(STGroupFile group, M vm, String dateiName)
			throws IOException {
		writer = erzeugeWriter(dateiName);
	}
	
	public void close() throws IOException {
		writer.close();
	}

	



}