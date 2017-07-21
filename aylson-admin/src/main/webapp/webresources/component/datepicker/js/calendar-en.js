/*  Copyright Mihai Bazon, 2002, 2003  |  http://students.infoiasi.ro/~mishoo
 * ---------------------------------------------------------------------------
 *
 * The DHTML Calendar, version 0.9.3 "It's still alive & keeps rocking"
 *
 * Details and latest version at:
 * http://students.infoiasi.ro/~mishoo/site/calendar.epl
 *
 * Feel free to use this script under the terms of the GNU Lesser General
 * Public License, as long as you do not remove or alter this notice.
 */

// $Id: calendar-en.js,v 1.1 2011/12/16 01:11:19 administrator Exp $

/** The Calendar object constructor. */
Calendar._DN = new Array
("Sun",
 "Mon",
 "Tue",
 "Wed",
 "Thu",
 "Fri",
 "Sat",
 "Sun");
Calendar._MN = new Array
("Jan",
 "Feb",
 "Mar",
 "Apr",
 "May",
 "Jun",
 "Jul",
 "Aug",
 "Sep",
 "Oct",
 "Nov",
 "Dec");

// tooltips
Calendar._TT = {};
Calendar._TT["TOGGLE"] = "";
Calendar._TT["PREV_YEAR"] = "Prev Year";
Calendar._TT["PREV_MONTH"] = "Prev Month";
Calendar._TT["GO_TODAY"] = "Today";
Calendar._TT["NEXT_MONTH"] = "Next Month";
Calendar._TT["NEXT_YEAR"] = "Next Year";
Calendar._TT["SEL_DATE"] = "Select Date";
Calendar._TT["DRAG_TO_MOVE"] = "Drag";
Calendar._TT["PART_TODAY"] = " (Today)";
Calendar._TT["MON_FIRST"] = "Monday First";
Calendar._TT["SUN_FIRST"] = "Sunday First";
Calendar._TT["CLOSE"] = "Close";
Calendar._TT["TODAY"] = "Today";

// date formats
Calendar._TT["DEF_DATE_FORMAT"] = "mm/dd/y";
Calendar._TT["TT_DATE_FORMAT"] = "D, M d";

Calendar._TT["WK"] = "Week";