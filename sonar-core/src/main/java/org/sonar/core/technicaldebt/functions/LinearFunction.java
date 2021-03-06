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
package org.sonar.core.technicaldebt.functions;

import org.sonar.api.issue.Issue;
import org.sonar.api.rules.Violation;
import org.sonar.core.technicaldebt.TechnicalDebtConverter;
import org.sonar.core.technicaldebt.TechnicalDebtRequirement;

import java.util.Collection;

public class LinearFunction extends AbstractFunction {

  public static final String FUNCTION_LINEAR = "linear";

  public static final double DEFAULT_VIOLATION_COST = 1.0;

  public LinearFunction(TechnicalDebtConverter converter) {
    super(converter);
  }

  public String getKey() {
    return FUNCTION_LINEAR;
  }

  public double costInHours(TechnicalDebtRequirement requirement, Collection<Violation> violations) {
    double points = 0.0;
    for (Violation violation : violations) {
      Double effortToFix = violation.getCost();
      points += effortToFix != null ? effortToFix : DEFAULT_VIOLATION_COST;
    }
    return points * getConverter().toDays(requirement.getRemediationFactor());
  }

  public long costInMinutes(TechnicalDebtRequirement requirement, Issue issue) {
    Double effortToFix = issue.effortToFix();
    double points = effortToFix != null ? effortToFix : DEFAULT_VIOLATION_COST;
    return Double.valueOf(points * factorInMinutes(requirement)).longValue();
  }
}
