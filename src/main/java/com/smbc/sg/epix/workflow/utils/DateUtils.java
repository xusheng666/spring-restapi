package com.smbc.sg.epix.workflow.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * Utility methods for Date
 *
 */
public class DateUtils {

  /**
   * Adjust From and To date to make them within 30 days date range.
   * 
   * If fromDate is null, then set fromDate to toDate minus 30 days. If toDate is null or the date
   * range is greater than 30 days, then set To date to 30 days after From date
   * 
   * 
   * @param fromDate From Date
   * @param toDate To Date
   */
  public static void adjustToOneMonthDateRange(LocalDate fromDate, LocalDate toDate) {

    if (Objects.isNull(fromDate) && Objects.isNull(toDate))
      return;

    if (Objects.isNull(fromDate))
      fromDate = toDate.minusDays(30);

    if (Objects.isNull(toDate) || Period.between(fromDate, toDate).getDays() > 30)
      toDate = fromDate.plusDays(30);
  }
}
