/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/package org.obeonetwork.spreadsheet.importer.extensions;

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
