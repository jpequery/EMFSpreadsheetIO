package org.obeonetwork.excel.exporter.extensions;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IRegistryEventListener;
import org.eclipse.core.runtime.Platform;

public class ExcelExporterExtensionListener implements IRegistryEventListener {
	private static final Object EXCEL_EXPORTER_TAG_EXTENSION = "exporter";

	private static final String EXCEL_EXPORTER_EXTENSION_POINT = "org.obeonetwork.excel.exporter";

	@Override
	public void added(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			parseExtension(extension);
		}
	}

	private void parseExtension(IExtension extension) {
		IConfigurationElement[] elements = extension.getConfigurationElements();
		for (IConfigurationElement element : elements) {
			if (EXCEL_EXPORTER_TAG_EXTENSION.equals(element.getName())) {
				ExcelExporterExtensionRegistry
						.addExtension(new ExcelExporterExtensionDescriptor(element));
			}
		}
	}

	@Override
	public void removed(IExtension[] extensions) {
		for (IExtension extension : extensions) {
			final IConfigurationElement[] configElements = extension.getConfigurationElements();
			for (IConfigurationElement elem : configElements) {
				if (EXCEL_EXPORTER_TAG_EXTENSION.equals (elem.getName())){
					final String extensionClassName = elem
							.getAttribute(ExcelExporterExtensionDescriptor.EXCEL_EXPORT_ATTRIBUTE_EXTENSION_NAME);
					ExcelExporterExtensionRegistry.removeExtension(extensionClassName);
				}
			}
		}
	}

	public void parseInitialContributions() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();

		for (IExtension extension : registry.getExtensionPoint(EXCEL_EXPORTER_EXTENSION_POINT)
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
