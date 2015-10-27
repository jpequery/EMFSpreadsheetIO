package org.obeonetwork.excel.exporter.extensions;

import java.util.ArrayList;
import java.util.List;

public class ExcelExporterExtensionRegistry {
	private static final List<ExcelExporterExtensionDescriptor> EXTENSIONS = new ArrayList<ExcelExporterExtensionDescriptor>();

	public static void addExtensionTemplateDescriptor(ExcelExporterExtensionDescriptor descriptor) {
		EXTENSIONS.add(descriptor);
	}

	public static void clearRegistry() {
		EXTENSIONS.clear();
	}

	public static final List<ExcelExporterExtensionDescriptor> getRegisteredExtensions() {
		return EXTENSIONS;
	}

	public static void removeExtension(String extensionClassName) {
		for (ExcelExporterExtensionDescriptor extension : getRegisteredExtensions()) {
			if (extension.getExtensionClassName().equals(extensionClassName))
				EXTENSIONS.remove(extension);
		}

	}

	public static void addExtension(
			ExcelExporterExtensionDescriptor ExcelImporterExtensionDescriptor) {
		addExtensionTemplateDescriptor(ExcelImporterExtensionDescriptor);
	}


}
