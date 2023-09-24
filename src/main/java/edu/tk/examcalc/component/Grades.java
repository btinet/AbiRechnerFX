package edu.tk.examcalc.component;

import java.util.Map;

public class Grades {

    public static final Map<Integer, Double> GRADE;

    static {
        GRADE = Map.ofEntries(
                Map.entry(300,4.0),
                Map.entry(301,3.9),
                Map.entry(319,3.8),
                Map.entry(337,3.7),
                Map.entry(355,3.6),
                Map.entry(373,3.5),
                Map.entry(391,3.4),
                Map.entry(409,3.3),
                Map.entry(427,3.2),
                Map.entry(445,3.1),
                Map.entry(463,3.0),
                Map.entry(481,2.9),
                Map.entry(499,2.8),
                Map.entry(517,2.7),
                Map.entry(535,2.6),
                Map.entry(553,2.5),
                Map.entry(571,2.4),
                Map.entry(589,2.3),
                Map.entry(607,2.2),
                Map.entry(625,2.1),
                Map.entry(643,2.0),
                Map.entry(661,1.9),
                Map.entry(679,1.8),
                Map.entry(697,1.7),
                Map.entry(715,1.6),
                Map.entry(733,1.5),
                Map.entry(751,1.4),
                Map.entry(769,1.3),
                Map.entry(787,1.2),
                Map.entry(805,1.1),
                Map.entry(823,1.0)
        );

    }

}
