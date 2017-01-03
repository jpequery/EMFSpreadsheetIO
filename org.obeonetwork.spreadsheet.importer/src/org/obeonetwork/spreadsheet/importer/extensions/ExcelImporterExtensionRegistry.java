package org.obeonetwork.spreadsheet.importer.extensions;

import java.util.ArrayList;
import java.util.List;

public class ExcelImporterExtensionRegistry {
	private static final List<ExcelImporterExtensionDescriptor> EXTENSIONS = new ArrayList<ExcelImporterExtensionDescriptor>();

	public static void addExtensionTemplateDescriptor(ExcelImporterExtensionDescriptor descriptor) {
		EXTENSIONS.add(descriptor);
	}

	public static void clearRegistry() {
		EXTENSIONS.clear();
	}

	public static final List<ExcelImporterExtensionDescriptor> getRegisteredExtensions() {
		return EXTENSIONS;
	}

	public static void removeExtension(String extensionClassName) {
		for (ExcelImporterExtensionDescriptor extension : getRegisteredExtensions()) {
			if (extension.getExtensionClassName().equals(extensionClassName))
				EXTENSIONS.remove(extension);
		}

	}

	public static void addExtension(
			ExcelImporterExtensionDescriptor ExcelImporterExtensionDescriptor) {
		addExtensionTemplateDescriptor(ExcelImporterExtensionDescriptor);
	}


}
