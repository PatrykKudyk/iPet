package com.partos.ipet.logic

import com.partos.ipet.models.Date

class DateHelper() {

    fun getDiffInSeconds(then: Date, now: Date): Long {
        var diff = 0L
        if (then.year == now.year) {
            if (then.month == now.month) {
                if (then.day == now.day) {
                    if (then.hour == now.hour) {
                        if (then.minute == now.minute) {
                            diff += (now.second - then.second)
                        } else {
                            diff += (now.minute - then.minute - 1) * 60 + (60 - then.second)
                        }
                    } else {
                        diff += (now.hour - then.hour - 1) * 3600 + (60 - then.minute - 1) * 60 + (60 - then.second)
                    }
                } else {
                    diff += (now.day - then.day - 1) * 86400 + (24 - then.hour - 1) * 3600 +
                            (60 - then.minute - 1) * 60 + (60 - then.second)
                }
            } else {
                diff += (getDayDiffMonth(then, now) - 1) * 86400 + (24 - then.hour - 1) * 3600 +
                        (60 - then.minute - 1) * 60 + (60 - then.second)
            }
        } else {
            diff += (getDayDiffYear(then, now) - 1) * 86400 + (24 - then.hour - 1) * 3600 +
                    (60 - then.minute - 1) * 60 + (60 - then.second)
        }
        return diff
    }

    private fun getDayDiffYear(then: Date, now: Date): Long {

        return 0L
    }

    private fun getDayDiffMonth(then: Date, now: Date): Long {
        when (then.month) {
            1 -> return getDayDiffMonthJanuary(then, now)
            2 -> return getDayDiffMonthFebruary(then, now)
            3 -> return getDayDiffMonthMarch(then, now)
            4 -> return getDayDiffMonthApril(then, now)
            5 -> return getDayDiffMonthMay(then, now)
            6 -> return getDayDiffMonthJune(then, now)
            7 -> return getDayDiffMonthJuly(then, now)
            8 -> return getDayDiffMonthAugust(then, now)
            9 -> return getDayDiffMonthSeptember(then, now)
            10 -> return getDayDiffMonthOctober(then, now)
            11 -> return getDayDiffMonthNovember(then, now)
        }
        return 0
    }

    private fun getDayDiffMonthJanuary(then: Date, now: Date): Long {
        when (now.month) {
            2 -> return (31 - then.day + now.day).toLong()
            3 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 29 + now.day).toLong()
                } else {
                    (31 - then.day + 28 + now.day).toLong()
                }
            }
            4 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 60 + now.day).toLong()
                } else {
                    (31 - then.day + 59 + now.day).toLong()
                }
            }
            5 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 90 + now.day).toLong()
                } else {
                    (31 - then.day + 89 + now.day).toLong()
                }
            }
            6 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 121 + now.day).toLong()
                } else {
                    (31 - then.day + 120 + now.day).toLong()
                }
            }
            7 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 151 + now.day).toLong()
                } else {
                    (31 - then.day + 150 + now.day).toLong()
                }
            }
            8 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 182 + now.day).toLong()
                } else {
                    (31 - then.day + 181 + now.day).toLong()
                }
            }
            9 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 213 + now.day).toLong()
                } else {
                    (31 - then.day + 212 + now.day).toLong()
                }
            }
            10 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 243 + now.day).toLong()
                } else {
                    (31 - then.day + 242 + now.day).toLong()
                }
            }
            11 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 274 + now.day).toLong()
                } else {
                    (31 - then.day + 273 + now.day).toLong()
                }
            }
            12 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (31 - then.day + 304 + now.day).toLong()
                } else {
                    (31 - then.day + 303 + now.day).toLong()
                }
            }
        }
        return 0L
    }

    private fun getDayDiffMonthFebruary(then: Date, now: Date): Long {
        when (now.month) {
            3 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + now.day).toLong()
                } else {
                    (28 - then.day + now.day).toLong()
                }
            }
            4 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 31 + now.day).toLong()
                } else {
                    (28 - then.day + 31 + now.day).toLong()
                }
            }
            5 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 61 + now.day).toLong()
                } else {
                    (28 - then.day + 61 + now.day).toLong()
                }
            }
            6 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 92 + now.day).toLong()
                } else {
                    (28 - then.day + 92 + now.day).toLong()
                }
            }
            7 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 122 + now.day).toLong()
                } else {
                    (28 - then.day + 122 + now.day).toLong()
                }
            }
            8 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 153 + now.day).toLong()
                } else {
                    (28 - then.day + 153 + now.day).toLong()
                }
            }
            9 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 184 + now.day).toLong()
                } else {
                    (28 - then.day + 184 + now.day).toLong()
                }
            }
            10 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 214 + now.day).toLong()
                } else {
                    (28 - then.day + 214 + now.day).toLong()
                }
            }
            11 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 245 + now.day).toLong()
                } else {
                    (28 - then.day + 245 + now.day).toLong()
                }
            }
            12 -> {
                return if ((now.year % 400 == 0) || ((now.year % 4 == 0) && (now.year % 100 != 0))) {
                    (29 - then.day + 275 + now.day).toLong()
                } else {
                    (28 - then.day + 275 + now.day).toLong()
                }
            }
        }
        return 0L
    }

    private fun getDayDiffMonthMarch(then: Date, now: Date): Long {
        when (now.month) {
            4 -> return (31 - then.day + now.day).toLong()
            5 -> return (61 - then.day + now.day).toLong()
            6 -> return (92 - then.day + now.day).toLong()
            7 -> return (122 - then.day + now.day).toLong()
            8 -> return (153 - then.day + now.day).toLong()
            9 -> return (184 - then.day + now.day).toLong()
            10 -> return (214 - then.day + now.day).toLong()
            11 -> return (245 - then.day + now.day).toLong()
            12 -> return (275 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthApril(then: Date, now: Date): Long {
        when (now.month) {
            5 -> return (30 - then.day + now.day).toLong()
            6 -> return (61 - then.day + now.day).toLong()
            7 -> return (91 - then.day + now.day).toLong()
            8 -> return (123 - then.day + now.day).toLong()
            9 -> return (154 - then.day + now.day).toLong()
            10 -> return (184 - then.day + now.day).toLong()
            11 -> return (215 - then.day + now.day).toLong()
            12 -> return (245 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthMay(then: Date, now: Date): Long {
        when (now.month) {
            6 -> return (31 - then.day + now.day).toLong()
            7 -> return (61 - then.day + now.day).toLong()
            8 -> return (92 - then.day + now.day).toLong()
            9 -> return (123 - then.day + now.day).toLong()
            10 -> return (153 - then.day + now.day).toLong()
            11 -> return (184 - then.day + now.day).toLong()
            12 -> return (214 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthJune(then: Date, now: Date): Long {
        when (now.month) {
            7 -> return (30 - then.day + now.day).toLong()
            8 -> return (61 - then.day + now.day).toLong()
            9 -> return (92 - then.day + now.day).toLong()
            10 -> return (122 - then.day + now.day).toLong()
            11 -> return (153 - then.day + now.day).toLong()
            12 -> return (183 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthJuly(then: Date, now: Date): Long {
        when (now.month) {
            8 -> return (31 - then.day + now.day).toLong()
            9 -> return (62 - then.day + now.day).toLong()
            10 -> return (92 - then.day + now.day).toLong()
            11 -> return (123 - then.day + now.day).toLong()
            12 -> return (153 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthAugust(then: Date, now: Date): Long {
        when (now.month) {
            9 -> return (31 - then.day + now.day).toLong()
            10 -> return (61 - then.day + now.day).toLong()
            11 -> return (92 - then.day + now.day).toLong()
            12 -> return (122 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthSeptember(then: Date, now: Date): Long {
        when (now.month) {
            10 -> return (30 - then.day + now.day).toLong()
            11 -> return (61 - then.day + now.day).toLong()
            12 -> return (91 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthOctober(then: Date, now: Date): Long {
        when (now.month) {
            11 -> return (31 - then.day + now.day).toLong()
            12 -> return (61 - then.day + now.day).toLong()
        }
        return 0L
    }

    private fun getDayDiffMonthNovember(then: Date, now: Date): Long {
        when (now.month) {
            12 -> return (31 - then.day + now.day).toLong()
        }
        return 0L
    }
}