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

import fr.paris.lutece.portal.service.plugin.Plugin;

import java.util.List;


/**
 * IDateCalculatedDAO
 */
public interface IDateCalculatedDAO
{
    /**
    * Creates a new instance of calculated date
    *
    * @param dateCalculated The instance of calculated date which contains the informations to store
    * @param plugin The plugin
    */
    void insert( DateCalculated dateCalculated, Plugin plugin );

    /**
     * Updates the calculated date whose identifier is specified in parameter
     *
     * @param dateCalculated The instance of calculated date which contains the informations to update
     * @param plugin The plugin
     */
    void store( DateCalculated dateCalculated, Plugin plugin );

    /**
     * Removes the calculated date whose identifier is specified in parameter
     *
     * @param nKey The calculated date primary key
     * @param plugin The plugin
     */
    void delete( int nKey, Plugin plugin );

    /**
     * Returns the calculated date whose identifier is specified in parameter
     *
     * @param nKey The calculated date primary key
     * @param plugin The plugin
     * @return The calculated date whose identifier is specified in parameter
     */
    DateCalculated load( int nKey, Plugin plugin );

    /**
     * Finds all calculated dates
     *
     * @param plugin The plugin
     * @return the list which contains all calculated dates
     */
    List<DateCalculated> selectAll( Plugin plugin );
}
