package tho.nill.grundgestein.ST;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.logging.Logger;

import org.stringtemplate.v4.STGroupFile;

public class STGesamtDateiAusgabe<M> extends STTemplate<M> {
	static Logger logger = Logger.getLogger(STGesamtDateiAusgabe.class.getSimpleName());

	public STGesamtDateiAusgabe(Charset charset) {
		super(charset);
	}

	protected void erzeugeAusgabe(M vorlageModell, String vorlageDatei, String ausgabeVerzeichnis) throws IOException {
		STGroupFile group = createGroupFile(vorlageDatei);
		register(group);
		erzeugeAusgabeAusVorlageModell(group, ausgabeVerzeichnis, vorlageModell);
	}

	protected void ausgabeAktion(STGroupFile group, M vm, String dateiName) throws IOException {
		Writer writer = erzeugeWriter(dateiName);
		erzeugeAusgabe(group, writer, vm);
		writer.close();
	}

	protected void erzeugeAusgabe(STGroupFile group, Writer writer, M modell) throws IOException {
		writer.write(apply(group, "dateiInhalt", modell));
		writer.flush();
	}

}