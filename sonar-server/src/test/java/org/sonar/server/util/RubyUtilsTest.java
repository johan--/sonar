/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.sonar.api.utils.DateUtils;
import org.sonar.api.utils.SonarException;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class RubyUtilsTest {

  @Rule
  public ExpectedException throwable = ExpectedException.none();

  @Test
  public void should_parse_list_of_strings() {
    assertThat(RubyUtils.toStrings(null)).isNull();
    assertThat(RubyUtils.toStrings("")).isEmpty();
    assertThat(RubyUtils.toStrings("foo")).containsOnly("foo");
    assertThat(RubyUtils.toStrings("foo,bar")).containsOnly("foo", "bar");
    assertThat(RubyUtils.toStrings(asList("foo", "bar"))).containsOnly("foo", "bar");
  }

  @Test
  public void toInteger() throws Exception {
    assertThat(RubyUtils.toInteger(null)).isNull();
    assertThat(RubyUtils.toInteger("")).isNull();
    assertThat(RubyUtils.toInteger("   ")).isNull();
    assertThat(RubyUtils.toInteger("123")).isEqualTo(123);
    assertThat(RubyUtils.toInteger(123)).isEqualTo(123);
    assertThat(RubyUtils.toInteger(123L)).isEqualTo(123);
  }

  @Test
  public void toInteger_unexpected_class() throws Exception {
    throwable.expect(IllegalArgumentException.class);

    RubyUtils.toInteger(1.2);
  }

  @Test
  public void toDouble() throws Exception {
    assertThat(RubyUtils.toDouble(null)).isNull();
    assertThat(RubyUtils.toDouble("")).isNull();
    assertThat(RubyUtils.toDouble("  ")).isNull();
    assertThat(RubyUtils.toDouble("123")).isEqualTo(123.0);
    assertThat(RubyUtils.toDouble("3.14")).isEqualTo(3.14);
    assertThat(RubyUtils.toDouble(3.14)).isEqualTo(3.14);
    assertThat(RubyUtils.toDouble(123)).isEqualTo(123.0);
    assertThat(RubyUtils.toDouble(123L)).isEqualTo(123.0);
  }

  @Test
  public void toDouble_unexpected_class() throws Exception {
    throwable.expect(IllegalArgumentException.class);

    RubyUtils.toDouble(true);
  }

  @Test
  public void toBoolean() throws Exception {
    assertThat(RubyUtils.toBoolean(null)).isNull();
    assertThat(RubyUtils.toBoolean("")).isNull();
    assertThat(RubyUtils.toBoolean("  ")).isNull();
    assertThat(RubyUtils.toBoolean("true")).isTrue();
    assertThat(RubyUtils.toBoolean(true)).isTrue();
    assertThat(RubyUtils.toBoolean("false")).isFalse();
    assertThat(RubyUtils.toBoolean(false)).isFalse();
  }

  @Test
  public void toBoolean_unexpected_class() throws Exception {
    throwable.expect(IllegalArgumentException.class);

    RubyUtils.toBoolean(333);
  }

  @Test
  public void toDate() throws Exception {
    assertThat(RubyUtils.toDate(null)).isNull();
    assertThat(RubyUtils.toDate("")).isNull();
    assertThat(RubyUtils.toDate("   ")).isNull();
    assertThat(RubyUtils.toDate("2013-01-18").getDate()).isEqualTo(18);
    assertThat(RubyUtils.toDate("2013-01-18T15:38:19+0200").getDate()).isEqualTo(18);
    assertThat(RubyUtils.toDate("2013-01-18T15:38:19+0200").getMinutes()).isEqualTo(38);
    assertThat(RubyUtils.toDate(DateUtils.parseDate("2013-01-18")).getDate()).isEqualTo(18);
  }

  @Test
  public void toDate_bad_format() throws Exception {
    throwable.expect(SonarException.class);

    RubyUtils.toDate("01/02/2013");
  }

  @Test
  public void toDate_unexpected_class() throws Exception {
    throwable.expect(IllegalArgumentException.class);

    RubyUtils.toDate(333);
  }

  @Test
  public void toLong() throws Exception {
    assertThat(RubyUtils.toLong(null)).isNull();
    assertThat(RubyUtils.toLong(2)).isEqualTo(2L);
    assertThat(RubyUtils.toLong(3L)).isEqualTo(3L);
    assertThat(RubyUtils.toLong("4")).isEqualTo(4L);
  }

  @Test
  public void toLong_unexpected_class() throws Exception {
    throwable.expect(IllegalArgumentException.class);

    RubyUtils.toLong(false);
  }
}
