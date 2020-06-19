package tho.nill.grundgestein.ST;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.StringRenderer;

public abstract class STTemplate<M> {
	static Logger logger = Logger.getLogger(STTemplate.class.getSimpleName());

	protected Charset charset;

	public STTemplate(Charset charset) {
		super();
		this.charset = charset;
	}

	
	protected void erzeugeAusgabeAusVorlageModell(STGroupFile group, String ausgabeVerzeichnis, M vm)
			throws IOException{
		String dateiName = getAusgabeDatei(group, ausgabeVerzeichnis, vm).toString();
		boolean überschreiben = isOverwrite(group, vm);
		boolean erzeugen = isCreate(group, vm);
		File d = new File(dateiName);
		logger.log(Level.INFO,"Dateiname: {0} ", dateiName);
		logger.log(Level.INFO,"Datei wird erzeugt: {0} ", (erzeugen) ? "an" : "aus");
		logger.log(Level.INFO,"Datei wird überschrieben: {0} ", (überschreiben) ? "an" : "aus");

		
		if (erzeugen) {
			if (überschreiben || !d.exists()) {
				logger.log(Level.INFO,"Ausgabe: {0} ", dateiName);
				logger.log(Level.INFO,"Absoluter Dateiname: {0} ", d.getAbsolutePath());
				erzeugeEventuellFehlendeVerzeichnisse(dateiName);
				ausgabeAktion(group, vm, dateiName);
			}
		} else {
			if (überschreiben && d.exists()) {
				Files.delete(d.toPath());
				if ( d.exists()) {
					throw new IllegalArgumentException("Konnte nicht gel�scht werden: "+ d.getAbsolutePath());
				}
			}
		}
	}

	protected abstract void ausgabeAktion(STGroupFile group, M vm, String dateiName) throws IOException;

	protected Writer erzeugeWriter(String dateName) throws FileNotFoundException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dateName), charset));
	}
	
	protected String apply(STGroupFile group, String templateName, M elem) {
		ST t = group.getInstanceOf(templateName);
		setzeSTModel(t, elem);
		return t.render();
	}
	
	protected void setzeSTModel(ST t, M elem) {
		t.add("model", elem);
	}

	protected void register(STGroupFile group) {
		group.registerModelAdaptor(Object.class, new MethodNameAdapter());
		group.registerRenderer(String.class, new StringRenderer());
	}

	protected STGroupFile createGroupFile(String vorlageDateiName) {
		logger.log(Level.INFO,"vorgabe = {0}",vorlageDateiName);
		return new STGroupFile(vorlageDateiName, '$', '$');
	}



	private File getAusgabeDatei(STGroupFile group, String ausgabeVerzeichnis, M modell) {
		return new File(ausgabeVerzeichnis, getDateiName(group, modell));
	}

	private String getDateiName(STGroupFile group, M modell) {
		return apply(group, "dateiName", modell);
	}

	private boolean isOverwrite(STGroupFile group, M modell) {
		return "true".equals(apply(group, "overwrite", modell));
	}

	private boolean isCreate(STGroupFile group, M modell) {
		return "true".equals(apply(group, "create", modell));
	}

	private void erzeugeEventuellFehlendeVerzeichnisse(String dateiName) throws IOException {
		File f = new File(dateiName);
		Files.createDirectories(f.getParentFile().toPath());
	}


}