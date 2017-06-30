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
 *******************************************************************************/package org.obeonetwork.spreadsheet.exporter;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

public interface IExcelMetamodelExtension {
	List<String> generateExtensions(EObject eObject, List<String> pvToExport);
}
