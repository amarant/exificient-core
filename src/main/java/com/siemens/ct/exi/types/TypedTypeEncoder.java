/*
 * Copyright (c) 2007-2016 Siemens AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

package com.siemens.ct.exi.types;

import java.io.IOException;
import java.util.Map;

import javax.xml.namespace.QName;

import com.siemens.ct.exi.context.QNameContext;
import com.siemens.ct.exi.datatype.Datatype;
import com.siemens.ct.exi.datatype.ListDatatype;
import com.siemens.ct.exi.datatype.strings.StringEncoder;
import com.siemens.ct.exi.exceptions.EXIException;
import com.siemens.ct.exi.io.channel.EncoderChannel;
import com.siemens.ct.exi.values.Value;

/**
 * 
 * @author Daniel.Peintner.EXT@siemens.com
 * @author Joerg.Heuer@siemens.com
 * 
 * @version 0.9.7-SNAPSHOT
 */

public class TypedTypeEncoder extends AbstractTypeEncoder {
	
	protected Datatype lastDatatype;
	protected final boolean doNormalize;

	public TypedTypeEncoder() throws EXIException {
		this(false);
	}
	
	public TypedTypeEncoder(boolean doNormalize) throws EXIException {
		this(null, null, null);
	}

	public TypedTypeEncoder(QName[] dtrMapTypes, QName[] dtrMapRepresentations, Map<QName, Datatype> dtrMapRepresentationsDatatype)
			throws EXIException {
		this(dtrMapTypes, dtrMapRepresentations, dtrMapRepresentationsDatatype, false);
	}
	
	public TypedTypeEncoder(QName[] dtrMapTypes, QName[] dtrMapRepresentations, Map<QName, Datatype> dtrMapRepresentationsDatatype, boolean doNormalize)
			throws EXIException {
		super(dtrMapTypes, dtrMapRepresentations, dtrMapRepresentationsDatatype);
		this.doNormalize = doNormalize;
	}

	public boolean isValid(Datatype datatype, Value value) {
		if (this.dtrMapInUse) {
			lastDatatype = this.getDtrDatatype(datatype);
		} else {
			lastDatatype = datatype;
		}

		return lastDatatype.isValid(value);
	}

	public void writeValue(QNameContext qnContext, EncoderChannel valueChannel,
			StringEncoder stringEncoder) throws IOException {
		if(doNormalize) {
			lastDatatype.normalize();
		}
		lastDatatype.writeValue(qnContext, valueChannel, stringEncoder);
	}
	
}
