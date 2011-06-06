/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.sql.DAOUtil;


/**
 * ComparatorDAO
 */
public class ComparatorDAO implements IComparatorDAO
{
    private static final String SQL_QUERY_SELECT_ALL = "SELECT id_comparator,name FROM form_date_validators_comparator";
    private static final String SQL_QUERY_FIND_BY_PRIMARY_KEY = "SELECT name,class FROM form_date_validators_comparator WHERE id_comparator=?";

    /**
    * Loads data from the comparator whose identifier is specified in parameter
    *
    * @param nIdComparator The comparator identifier
    * @param plugin The plugin
    * @return the comparator whose identifier is specified in parameter
    */
    public Comparator load( int nIdComparator, Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_PRIMARY_KEY, plugin );
        daoUtil.setInt( 1, nIdComparator );
        daoUtil.executeQuery(  );

        Comparator comparator = null;

        if ( daoUtil.next(  ) )
        {
            comparator = new Comparator(  );

            comparator.setIdComparator( nIdComparator );
            comparator.setName( daoUtil.getString( 1 ) );
            comparator.setClassName( daoUtil.getString( 2 ) );
        }

        daoUtil.free(  );

        return comparator;
    }

    /**
     * Loads data from all comparators
     *
     * @param plugin The plugin
     * @return the referenceList which contains data from all comparators
     */
    public ReferenceList selectAll( Plugin plugin )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, plugin );
        daoUtil.executeQuery(  );

        ReferenceList refListComparators = new ReferenceList(  );
        Comparator comparator;

        while ( daoUtil.next(  ) )
        {
            comparator = new Comparator(  );
            comparator.setIdComparator( daoUtil.getInt( 1 ) );
            comparator.setName( daoUtil.getString( 2 ) );

            refListComparators.addItem( comparator.getIdComparator(  ), comparator.getName(  ) );
        }

        daoUtil.free(  );

        return refListComparators;
    }
}
