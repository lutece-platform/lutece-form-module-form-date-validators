/*
 * Copyright (c) 2002-2017, Mairie de Paris
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

import java.sql.Timestamp;


/**
 * DateCalculated
 */
public class DateCalculated
{
    private int _nIdDateCalculated;
    private Timestamp _dateReference;
    private Operator _operator;
    private int _nNumber;
    private Unit _unit;

    /**
     * Gets the calculated date identifier
     * @return the calculated date identifier
     */
    public int getIdDateCalculated(  )
    {
        return _nIdDateCalculated;
    }

    /**
     * Sets the calculated date identifier
     * @param nIdDateCalculated the calculated date identifier
     */
    public void setIdDateCalculated( int nIdDateCalculated )
    {
        _nIdDateCalculated = nIdDateCalculated;
    }

    /**
     * Gets the reference date
     * @return the reference date
     */
    public Timestamp getDateReference(  )
    {
        return ( _dateReference != null ) ? new Timestamp( _dateReference.getTime(  ) ) : null;
    }

    /**
     * Sets the reference date
     * @param dateReference the reference date
     */
    public void setDateReference( Timestamp dateReference )
    {
        _dateReference = ( dateReference != null ) ? new Timestamp( dateReference.getTime(  ) ) : null;
    }

    /**
     * Gets the operator
     * @return the operator
     */
    public Operator getOperator(  )
    {
        return _operator;
    }

    /**
     * Sets the operator
     * @param operator the operator
     */
    public void setOperator( Operator operator )
    {
        _operator = operator;
    }

    /**
     * Gets the number
     * @return the number
     */
    public int getNumber(  )
    {
        return _nNumber;
    }

    /**
     * Sets the number
     * @param nNumber the number
     */
    public void setNumber( int nNumber )
    {
        _nNumber = nNumber;
    }

    /**
     * Gets the unit
     * @return the unit
     */
    public Unit getUnit(  )
    {
        return _unit;
    }

    /**
     * Sets the unit
     * @param unit the unit
     */
    public void setUnit( Unit unit )
    {
        _unit = unit;
    }
}
