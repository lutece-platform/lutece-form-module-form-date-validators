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


/**
 * Operator
 */
public class Operator
{
    private int _nIdOperator;
    private String _strName;
    private String _strClassName;

    /**
     * Gets the operator identifier
     * @return the operator identifier
     */
    public int getIdOperator(  )
    {
        return _nIdOperator;
    }

    /**
     * Sets the operator identifier
     * @param nIdOperator the operator identifier
     */
    public void setIdOperator( int nIdOperator )
    {
        _nIdOperator = nIdOperator;
    }

    /**
     * Gets the operator name
     * @return the operator name
     */
    public String getName(  )
    {
        return _strName;
    }

    /**
     * Sets the operator name
     * @param strName the operator name
     */
    public void setName( String strName )
    {
        _strName = strName;
    }

    /**
     * Gets the operator class name
     * @return the operator class name
     */
    public String getClassName(  )
    {
        return _strClassName;
    }

    /**
     * Sets the operator class name
     * @param strClassName the operator class name
     */
    public void setClassName( String strClassName )
    {
        _strClassName = strClassName;
    }
}
