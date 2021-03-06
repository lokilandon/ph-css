/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.css.media;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ICloneable;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.CollectionHelper;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Manages an ordered set of {@link ECSSMedium} objects.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class CSSMediaList implements ICSSMediaList, ICloneable <CSSMediaList>
{
  public static final String DEFAULT_MEDIA_STRING_SEPARATOR = ", ";

  // Ordered but unique
  private final Set <ECSSMedium> m_aMedia = new LinkedHashSet <ECSSMedium> ();

  /**
   * Constructor
   */
  public CSSMediaList ()
  {}

  /**
   * Constructor with a single medium
   *
   * @param eMedium
   *        The medium to be added. May not be <code>null</code>.
   */
  public CSSMediaList (@Nonnull final ECSSMedium eMedium)
  {
    addMedium (eMedium);
  }

  /**
   * Constructor with an array of media.
   *
   * @param aMedia
   *        The array of media to be added. The array may be <code>null</code>
   *        but it may not contain <code>null</code> elements.
   */
  public CSSMediaList (@Nullable final ECSSMedium... aMedia)
  {
    if (aMedia != null)
      for (final ECSSMedium eMedium : aMedia)
        addMedium (eMedium);
  }

  /**
   * Constructor with a collection of media.
   *
   * @param aMedia
   *        The collection of media to be added. The collection may be
   *        <code>null</code> but it may not contain <code>null</code> elements.
   */
  public CSSMediaList (@Nullable final Iterable <ECSSMedium> aMedia)
  {
    if (aMedia != null)
      for (final ECSSMedium eMedium : aMedia)
        addMedium (eMedium);
  }

  /**
   * Constructor using another media list.
   *
   * @param aOther
   *        The object to copy from. May not be <code>null</code>.
   * @since 3.8.3
   */
  public CSSMediaList (@Nonnull final ICSSMediaList aOther)
  {
    ValueEnforcer.notNull (aOther, "Other");
    m_aMedia.addAll (aOther.getAllMedia ());
  }

  /**
   * Copy constructor.
   *
   * @param aOther
   *        The object to copy from. May not be <code>null</code>.
   */
  public CSSMediaList (@Nonnull final CSSMediaList aOther)
  {
    ValueEnforcer.notNull (aOther, "Other");
    m_aMedia.addAll (aOther.m_aMedia);
  }

  /**
   * Add a new medium to the list
   *
   * @param eMedium
   *        The medium to be added. May not be <code>null</code>.
   * @return <code>this</code>
   */
  @Nonnull
  public CSSMediaList addMedium (@Nonnull final ECSSMedium eMedium)
  {
    ValueEnforcer.notNull (eMedium, "Medium");

    m_aMedia.add (eMedium);
    return this;
  }

  /**
   * Add a media list to the list
   *
   * @param aMediaList
   *        The media list to be added. May not be <code>null</code>.
   * @return <code>this</code>
   * @since 3.8.3
   */
  @Nonnull
  public CSSMediaList addMedia (@Nonnull final ECSSMedium... aMediaList)
  {
    ValueEnforcer.notNull (aMediaList, "MediaList");

    for (final ECSSMedium eMedium : aMediaList)
      m_aMedia.add (eMedium);
    return this;
  }

  /**
   * Add a media list to the list
   *
   * @param aMediaList
   *        The media list to be added. May not be <code>null</code>.
   * @return <code>this</code>
   * @since 3.8.3
   */
  @Nonnull
  public CSSMediaList addMedia (@Nonnull final ICSSMediaList aMediaList)
  {
    ValueEnforcer.notNull (aMediaList, "MediaList");

    m_aMedia.addAll (aMediaList.getAllMedia ());
    return this;
  }

  /**
   * Add a media list to the list
   *
   * @param aMediaList
   *        The media list to be added. May not be <code>null</code>.
   * @return <code>this</code>
   * @since 3.8.3
   */
  @Nonnull
  public CSSMediaList addMedia (@Nonnull final Iterable <ECSSMedium> aMediaList)
  {
    ValueEnforcer.notNull (aMediaList, "MediaList");

    for (final ECSSMedium eMedium : aMediaList)
      m_aMedia.add (eMedium);
    return this;
  }

  /**
   * Remove the passed medium
   *
   * @param eMedium
   *        The medium to be removed. May be <code>null</code>.
   * @return {@link EChange#CHANGED} if the medium was removed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  public EChange removeMedium (@Nullable final ECSSMedium eMedium)
  {
    return EChange.valueOf (m_aMedia.remove (eMedium));
  }

  /**
   * Remove all media.
   *
   * @return {@link EChange#CHANGED} if any medium was removed,
   *         {@link EChange#UNCHANGED} otherwise. Never <code>null</code>.
   * @since 3.7.3
   */
  @Nonnull
  public EChange removeAllMedia ()
  {
    if (m_aMedia.isEmpty ())
      return EChange.UNCHANGED;
    m_aMedia.clear ();
    return EChange.CHANGED;
  }

  @Nonnegative
  public int getMediaCount ()
  {
    return m_aMedia.size ();
  }

  public boolean hasAnyMedia ()
  {
    return !m_aMedia.isEmpty ();
  }

  public boolean hasNoMedia ()
  {
    return m_aMedia.isEmpty ();
  }

  public boolean hasNoMediaOrAll ()
  {
    return hasNoMedia () || containsMedium (ECSSMedium.ALL);
  }

  public boolean containsMedium (@Nullable final ECSSMedium eMedium)
  {
    return m_aMedia.contains (eMedium);
  }

  public boolean containsMediumOrAll (@Nullable final ECSSMedium eMedium)
  {
    // Either the specific medium is contained, or the "all" medium is contained
    return containsMedium (eMedium) || containsMedium (ECSSMedium.ALL);
  }

  public boolean isForScreen ()
  {
    // Default is "screen" if none is provided
    return hasNoMedia () || containsMediumOrAll (ECSSMedium.SCREEN);
  }

  @Nonnull
  @ReturnsMutableCopy
  public Set <ECSSMedium> getAllMedia ()
  {
    return CollectionHelper.newSortedSet (m_aMedia);
  }

  @Nonnull
  public String getMediaString ()
  {
    return getMediaString (DEFAULT_MEDIA_STRING_SEPARATOR);
  }

  @Nonnull
  public String getMediaString (@Nonnull final String sSeparator)
  {
    ValueEnforcer.notNull (sSeparator, "Separator");

    if (m_aMedia.isEmpty ())
      return "";

    final StringBuilder aSB = new StringBuilder ();
    for (final ECSSMedium eMedia : m_aMedia)
    {
      if (aSB.length () > 0)
        aSB.append (sSeparator);
      aSB.append (eMedia.getName ());
    }
    return aSB.toString ();
  }

  @Nonnegative
  public int size ()
  {
    return getMediaCount ();
  }

  public boolean isEmpty ()
  {
    return hasNoMedia ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public CSSMediaList getClone ()
  {
    return new CSSMediaList (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CSSMediaList rhs = (CSSMediaList) o;
    return m_aMedia.equals (rhs.m_aMedia);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aMedia).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("media", m_aMedia).toString ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public static CSSMediaList createOnDemand (@Nullable final ICSSMediaList aMediaList)
  {
    return aMediaList == null ? new CSSMediaList () : new CSSMediaList (aMediaList);
  }
}
