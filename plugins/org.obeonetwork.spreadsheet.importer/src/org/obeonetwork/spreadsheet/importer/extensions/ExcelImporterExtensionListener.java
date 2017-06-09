package org.obeonetwork.spreadsheet.importer.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

public class ExcelImporterExtensionListener implements IRegistryEventListener {
	private static final Object EXCEL_IMPORTER_TAG_EXTENSION = "importer";

	private static final String EXCEL_IMPORTER_EXTENSION_POINT = "org.obeonetwork.excel.importer";

	@Override
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			parseExtension(extension);
		}
	}

	private void parseExtension(IExtension extension) {
		IConfigurationElement[] elements = extension.getConfigurationElements();
		for (IConfigurationElement element : elements) {
			if (EXCEL_IMPORTER_TAG_EXTENSION.equals(element.getName())) {
				ExcelImporterExtensionRegistry
						.addExtension(new ExcelImporterExtensionDescriptor(element));
			}
		}
	}

	@Override
	public void removed(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElements) {
				if (EXCEL_IMPORTER_TAG_EXTENSION.equals (elem.getName())){
					final String extensionClassName = elem
							.getAttribute(ExcelImporterExtensionDescriptor.EXCEL_IMPORT_ATTRIBUTE_EXTENSION_NAME);
					ExcelImporterExtensionRegistry.removeExtension(extensionClassName);
				}
			}
		}
	}

	public void parseInitialContributions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();

		for (IExtension extension : registry.getExtensionPoint(EXCEL_IMPORTER_EXTENSION_POINT)
				.getExtensions()) {
			parseExtension(extension);
		}
	}
	
	@Override
	public void added(IExtensionPoint[] extensionPoints) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removed(IExtensionPoint[] extensionPoints) {
		// TODO Auto-generated method stub

	}



}
