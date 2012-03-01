/*
 * Copyright (c) 2002-2012, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.form.modules.datevalidators.business;

import java.util.Date;


/**
 * Rule
 */
public class Rule
{
    public static final String RESOURCE_TYPE = "FORM_DATE_RULE";
    private int _nIdRule;
    private int _nIdForm;
    private int _nIdEntry1;
    private int _nIdComparator;
    private Integer _nIdEntry2;
    private Date _dateReference;
    private DateCalculated _dateCalculated;

    /**
     * Gets the rule identifier
     * @return the rule identifier
     */
    public int getIdRule(  )
    {
        return _nIdRule;
    }

    /**
     * Sets the rule identifier
     * @param nIdRule the rule identifier
     */
    public void setIdRule( int nIdRule )
    {
        _nIdRule = nIdRule;
    }

    /**
     * Gets the form identifier
     * @return the form identifier
     */
    public int getIdForm(  )
    {
        return _nIdForm;
    }

    /**
     * Sets the form identifier
     * @param nIdForm the form identifier
     */
    public void setIdForm( int nIdForm )
    {
        _nIdForm = nIdForm;
    }

    /**
     * Gets the first entry identifier
     * @return the first entry identifier
     */
    public int getIdEntry1(  )
    {
        return _nIdEntry1;
    }

    /**
     * Sets the first entry identifier
     * @param nIdEntry1 the first entry identifier
     */
    public void setIdEntry1( int nIdEntry1 )
    {
        _nIdEntry1 = nIdEntry1;
    }

    /**
     * Gets the comparator identifier
     * @return the comparator identifier
     */
    public int getIdComparator(  )
    {
        return _nIdComparator;
    }

    /**
     * Sets the comparator identifier
     * @param nIdComparator the comparator identifier
     */
    public void setIdComparator( int nIdComparator )
    {
        _nIdComparator = nIdComparator;
    }

    /**
     * Gets the second entry identifier
     * @return the second entry identifier
     */
    public Integer getIdEntry2(  )
    {
        return _nIdEntry2;
    }

    /**
     * Sets the second entry identifier
     * @param nIdEntry2 the second entry identifier
     */
    public void setIdEntry2( Integer nIdEntry2 )
    {
        _nIdEntry2 = nIdEntry2;
    }

    /**
     * Gets the reference date
     * @return the reference date
     */
    public Date getDateReference(  )
    {
        return ( _dateReference != null ) ? new Date( _dateReference.getTime(  ) ) : null;
    }

    /**
     * Sets the reference date
     * @param dateReference the reference date
     */
    public void setDateReference( Date dateReference )
    {
        _dateReference = ( dateReference != null ) ? new Date( dateReference.getTime(  ) ) : null;
    }

    /**
     * Gets the calculated date
     * @return the calculated date
     */
    public DateCalculated getDateCalculated(  )
    {
        return _dateCalculated;
    }

    /**
     * Sets the calculated date
     * @param dateCalculated the calculated date
     */
    public void setDateCalculated( DateCalculated dateCalculated )
    {
        _dateCalculated = dateCalculated;
    }
}
