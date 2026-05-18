package com.nallanudi.app.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nallanudi.app.data.model.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {Term.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TermDao termDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "nallanudi_db"
                    )
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadExecutor().execute(() ->
                                    INSTANCE.termDao().insertAll(prepopulateData())
                            );
                        }
                    })
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static List<Term> prepopulateData() {
        List<Term> terms = new ArrayList<>();

        // ========== SCIENCE ==========
        terms.add(new Term("Photosynthesis", "ದ್ಯುತಿಸಂಶ್ಲೇಷಣೆ",
                "ಸಸ್ಯಗಳು ಸೂರ್ಯನ ಬೆಳಕನ್ನು ಬಳಸಿ ಆಹಾರ ತಯಾರಿಸುತ್ತವೆ",
                "Science", "fo-to-SIN-the-sis"));
        terms.add(new Term("Gravity", "ಗುರುತ್ವಾಕರ್ಷಣೆ",
                "ನಾವು ನೆಲಕ್ಕೆ ಬೀಳದಂತೆ ಹಿಡಿದಿಡುವ ಶಕ್ತಿ",
                "Science", "GRAV-i-tee"));
        terms.add(new Term("Osmosis", "ಅಭಿಸರಣ",
                "ನೀರು ತೆಳ್ಳನೆಯ ಪೊರೆ ಮೂಲಕ ಹಾದು ಹೋಗುವ ಪ್ರಕ್ರಿಯೆ",
                "Science", "oz-MOH-sis"));
        terms.add(new Term("Mitosis", "ಮೈಟೋಸಿಸ್",
                "ಒಂದು ಕೋಶ ಎರಡು ಕೋಶಗಳಾಗಿ ವಿಭಜಿಸುವ ಪ್ರಕ್ರಿಯೆ",
                "Science", "my-TOH-sis"));
        terms.add(new Term("Evaporation", "ಆವಿಯಾಗುವಿಕೆ",
                "ನೀರು ಶಾಖದಿಂದ ಆವಿಯಾಗಿ ಮಾರ್ಪಡುವುದು",
                "Science", "ih-vap-uh-RAY-shun"));
        terms.add(new Term("Respiration", "ಉಸಿರಾಟ",
                "ಆಮ್ಲಜನಕ ತೆಗೆದುಕೊಂಡು ಇಂಗಾಲದ ಡೈ ಆಕ್ಸೈಡ್ ಬಿಡುವ ಪ್ರಕ್ರಿಯೆ",
                "Science", "res-pih-RAY-shun"));
        terms.add(new Term("Chlorophyll", "ಹಸಿರು ವರ್ಣದ್ರವ್ಯ",
                "ಸಸ್ಯಗಳಿಗೆ ಹಸಿರು ಬಣ್ಣ ನೀಡುವ ವಸ್ತು",
                "Science", "KLOR-uh-fil"));
        terms.add(new Term("Nucleus", "ಕೋಶ ಕೇಂದ್ರ",
                "ಕೋಶದ ನಿಯಂತ್ರಣ ಕೇಂದ್ರ - ಮೆದುಳಿನಂತೆ",
                "Science", "NOO-klee-us"));
        terms.add(new Term("Atom", "ಪರಮಾಣು",
                "ವಸ್ತುವಿನ ಅತ್ಯಂತ ಚಿಕ್ಕ ಕಣ",
                "Science", "AT-um"));
        terms.add(new Term("Molecule", "ಅಣು",
                "ಎರಡು ಅಥವಾ ಹೆಚ್ಚು ಪರಮಾಣುಗಳ ಗುಂಪು",
                "Science", "MOL-ih-kyool"));
        terms.add(new Term("Ecosystem", "ಪರಿಸರ ವ್ಯವಸ್ಥೆ",
                "ಜೀವಿಗಳು ಮತ್ತು ಪರಿಸರ ಒಟ್ಟಿಗೆ ವಾಸಿಸುವ ವ್ಯವಸ್ಥೆ",
                "Science", "EE-koh-sis-tem"));
        terms.add(new Term("Combustion", "ದಹನ",
                "ಆಮ್ಲಜನಕದ ಸಹಾಯದಿಂದ ವಸ್ತು ಸುಡುವ ಪ್ರಕ್ರಿಯೆ",
                "Science", "kum-BUS-chun"));
        terms.add(new Term("Acceleration", "ತ್ವರಣ",
                "ಕಾರು ಹಠಾತ್ ಬ್ರೇಕ್ ಹಾಕಿದಾಗ ಋಣ ತ್ವರಣ ಉಂಟಾಗುತ್ತದೆ",
                "Science", "Ak-sel-er-ay-shun"));
        terms.add(new Term("Density", "ಸಾಂದ್ರತೆ",
                "ಒಂದು ಘಟಕ ಗಾತ್ರದಲ್ಲಿ ಎಷ್ಟು ದ್ರವ್ಯರಾಶಿ ಇದೆ ಎಂಬುದು",
                "Science", "DEN-si-tee"));
        terms.add(new Term("Friction", "ಘರ್ಷಣೆ",
                "ಎರಡು ಮೇಲ್ಮೈಗಳು ಉಜ್ಜಿದಾಗ ಉಂಟಾಗುವ ಬಲ",
                "Science", "FRIK-shun"));
        terms.add(new Term("Algorithm", "ಅಲ್ಗಾರಿದಮ್",
                "ಅಡುಗೆ ಮಾಡಲು ರೆಸಿಪಿ ಅನುಸರಿಸುವಂತೆ ಕಂಪ್ಯೂಟರ್ ಅಲ್ಗಾರಿದಮ್ ಅನುಸರಿಸುತ್ತದೆ",
                "Science", "Al-go-ri-thum"));
        terms.add(new Term("Magnetic Field", "ಕಾಂತ ಕ್ಷೇತ್ರ",
                "ಚುಂಬಕದ ಸುತ್ತ ಇರುವ ಆಕರ್ಷಣಾ ಶಕ್ತಿಯ ಪ್ರದೇಶ",
                "Science", "mag-NET-ik feeld"));
        terms.add(new Term("Oxidation", "ಆಕ್ಸೀಕರಣ",
                "ಕಬ್ಬಿಣ ತುಕ್ಕು ಹಿಡಿಯುವುದು ಆಕ್ಸೀಕರಣಕ್ಕೆ ಉದಾಹರಣೆ",
                "Science", "ok-si-DAY-shun"));

        // ========== MATH ==========
        terms.add(new Term("Algebra", "ಬೀಜಗಣಿತ",
                "x + 5 = 10 ಎಂದರೆ x = 5 ಎಂದು ಕಂಡುಹಿಡಿಯುವುದು",
                "Math", "Al-je-bra"));
        terms.add(new Term("Trigonometry", "ತ್ರಿಕೋನಮಿತಿ",
                "ತ್ರಿಕೋನಗಳ ಕೋನ ಮತ್ತು ಭುಜಗಳನ್ನು ಅಳೆಯುವ ಗಣಿತ",
                "Math", "trig-uh-NOM-ih-tree"));
        terms.add(new Term("Geometry", "ರೇಖಾಗಣಿತ",
                "ಆಕಾರ, ಅಳತೆ ಮತ್ತು ಸ್ಥಾನಗಳ ಬಗ್ಗೆ ಅಧ್ಯಯನ",
                "Math", "jee-OM-ih-tree"));
        terms.add(new Term("Polynomial", "ಬಹುಪದ",
                "ಒಂದಕ್ಕಿಂತ ಹೆಚ್ಚು ಪದಗಳಿರುವ ಗಣಿತ ಅಭಿವ್ಯಕ್ತಿ",
                "Math", "pol-ee-NOH-mee-ul"));
        terms.add(new Term("Quadratic", "ವರ್ಗ ಸಮೀಕರಣ",
                "x² ಇರುವ ಸಮೀಕರಣ. ಉದಾ: x² + 5x + 6 = 0",
                "Math", "kwod-RAT-ik"));
        terms.add(new Term("Factorial", "ಗುಣಾಂಕ",
                "1 ರಿಂದ ಆ ಸಂಖ್ಯೆವರೆಗೆ ಎಲ್ಲ ಸಂಖ್ಯೆಗಳ ಗುಣಲಬ್ಧ. 5! = 120",
                "Math", "fak-TOR-ee-ul"));
        terms.add(new Term("Hypothesis", "ಊಹೆ",
                "ಸಾಬೀತು ಮಾಡಲು ಮುಂದಿಡುವ ಹೇಳಿಕೆ",
                "Math", "hy-POTH-ih-sis"));
        terms.add(new Term("Perimeter", "ಪರಿಧಿ",
                "ಯಾವುದೇ ಆಕಾರದ ಸುತ್ತಳತೆ",
                "Math", "peh-RIM-ih-ter"));
        terms.add(new Term("Circumference", "ವೃತ್ತ ಪರಿಧಿ",
                "ವೃತ್ತದ ಸುತ್ತಳತೆ. C = 2πr",
                "Math", "ser-KUM-fuh-rens"));
        terms.add(new Term("Denominator", "ಛೇದ",
                "ಭಿನ್ನರಾಶಿಯಲ್ಲಿ ಕೆಳಗಿನ ಸಂಖ್ಯೆ. ½ ರಲ್ಲಿ 2 ಛೇದ",
                "Math", "dih-NOM-ih-nay-ter"));
        terms.add(new Term("Numerator", "ಅಂಶ",
                "ಭಿನ್ನರಾಶಿಯಲ್ಲಿ ಮೇಲಿನ ಸಂಖ್ಯೆ. ½ ರಲ್ಲಿ 1 ಅಂಶ",
                "Math", "NOO-muh-ray-ter"));
        terms.add(new Term("Probability", "ಸಂಭಾವ್ಯತೆ",
                "ನಾಣ್ಯ ಎಸೆದಾಗ ತಲೆ ಬರಲು 1/2 ಸಂಭಾವ್ಯತೆ ಇರುತ್ತದೆ",
                "Math", "prob-uh-BIL-ih-tee"));
        terms.add(new Term("Integer", "ಪೂರ್ಣಾಂಕ",
                "ದಶಮಾಂಶ ಇಲ್ಲದ ಸಂಖ್ಯೆ. ಉದಾ: -3, -2, 0, 1, 2",
                "Math", "IN-tih-jer"));
        terms.add(new Term("Theorem", "ಪ್ರಮೇಯ",
                "ಪೈಥಾಗೋರಸ್ ಪ್ರಮೇಯ ಒಂದು ಪ್ರಸಿದ್ಧ ಸಾಬೀತಾದ ಗಣಿತ ನಿಯಮ",
                "Math", "THEE-uh-rem"));
        terms.add(new Term("Logarithm", "ಲಾಗರಿದಮ್",
                "ಯಾವ ಘಾತಕ್ಕೆ ಸಂಖ್ಯೆ ಏರಿಸಬೇಕು ಎಂದು ತಿಳಿಸುತ್ತದೆ",
                "Math", "LOG-uh-rith-um"));
        terms.add(new Term("Matrix", "ಮ್ಯಾಟ್ರಿಕ್ಸ್",
                "ಸಂಖ್ಯೆಗಳನ್ನು ಸಾಲು ಮತ್ತು ಅಂಕಣಗಳಲ್ಲಿ ಜೋಡಿಸಿದ ಕೋಷ್ಟಕ",
                "Math", "MAY-triks"));

        // ========== COMMERCE ==========
        terms.add(new Term("Supply", "ಪೂರೈಕೆ",
                "ಮಾರುಕಟ್ಟೆಯಲ್ಲಿ ಮಾರಾಟಕ್ಕೆ ಲಭ್ಯವಿರುವ ಸರಕು ಅಥವಾ ಸೇವೆ",
                "Commerce", "Su-ply"));
        terms.add(new Term("Demand", "ಬೇಡಿಕೆ",
                "ಗ್ರಾಹಕರು ನಿರ್ದಿಷ್ಟ ಬೆಲೆಯಲ್ಲಿ ಖರೀದಿಸಲು ಇಚ್ಛಿಸುವ ಸರಕಿನ ಪ್ರಮಾಣ",
                "Commerce", "dih-MAND"));
        terms.add(new Term("Depreciation", "ಮೌಲ್ಯ ಇಳಿಕೆ",
                "ಕಾಲಕ್ರಮೇಣ ಸ್ವತ್ತಿನ ಮೌಲ್ಯ ಕಡಿಮೆ ಆಗುವುದು. ಹಳೆ ಕಾರಿನಂತೆ",
                "Commerce", "deh-pree-shee-AY-shun"));
        terms.add(new Term("Inflation", "ಹಣದುಬ್ಬರ",
                "ವಸ್ತುಗಳ ಬೆಲೆ ಏರಿಕೆ ಆಗಿ ಹಣದ ಮೌಲ್ಯ ಕಡಿಮೆ ಆಗುವುದು",
                "Commerce", "in-FLAY-shun"));
        terms.add(new Term("Dividend", "ಲಾಭಾಂಶ",
                "ಕಂಪನಿ ತನ್ನ ಷೇರುದಾರರಿಗೆ ನೀಡುವ ಲಾಭದ ಪಾಲು",
                "Commerce", "DIV-ih-dend"));
        terms.add(new Term("Liability", "ಹೊಣೆಗಾರಿಕೆ",
                "ಕಂಪನಿ ಮಾಡಿರುವ ಸಾಲ ಅಥವಾ ಪಾವತಿಸಬೇಕಾದ ಹಣ",
                "Commerce", "ly-uh-BIL-ih-tee"));
        terms.add(new Term("Asset", "ಸ್ವತ್ತು",
                "ಕಂಪನಿ ಹೊಂದಿರುವ ಬೆಲೆ ಬಾಳುವ ವಸ್ತು. ಭೂಮಿ, ಕಟ್ಟಡ ಇತ್ಯಾದಿ",
                "Commerce", "AS-et"));
        terms.add(new Term("Audit", "ಲೆಕ್ಕ ಪರಿಶೋಧನೆ",
                "ಕಂಪನಿ ಲೆಕ್ಕ ಪತ್ರಗಳನ್ನು ಪರಿಶೀಲಿಸುವ ಪ್ರಕ್ರಿಯೆ",
                "Commerce", "AW-dit"));
        terms.add(new Term("Balance Sheet", "ಸಮತೋಲನ ಪತ್ರ",
                "ಕಂಪನಿ ಸ್ವತ್ತು ಮತ್ತು ಹೊಣೆಗಾರಿಕೆ ತೋರಿಸುವ ಹಣಕಾಸು ಹೇಳಿಕೆ",
                "Commerce", "BAL-uns SHEET"));
        terms.add(new Term("Revenue", "ಆದಾಯ",
                "ವ್ಯಾಪಾರದಿಂದ ಗಳಿಸುವ ಒಟ್ಟು ಹಣ",
                "Commerce", "REV-eh-nyoo"));
        terms.add(new Term("Subsidy", "ಸಹಾಯಧನ",
                "ಸರ್ಕಾರ ನೀಡುವ ಹಣಕಾಸು ಬೆಂಬಲ",
                "Commerce", "SUB-sih-dee"));
        terms.add(new Term("Mortgage", "ಅಡಮಾನ",
                "ಆಸ್ತಿಯನ್ನು ಭದ್ರತೆಯಾಗಿಟ್ಟು ತೆಗೆದ ಸಾಲ",
                "Commerce", "MOR-gij"));
        terms.add(new Term("Budget", "ಬಜೆಟ್",
                "ಸರ್ಕಾರ ಅಥವಾ ಕಂಪನಿ ವರ್ಷದ ಆದಾಯ-ವೆಚ್ಚ ಯೋಜನೆ",
                "Commerce", "BUJ-it"));
        terms.add(new Term("Investment", "ಹೂಡಿಕೆ",
                "ಭವಿಷ್ಯದಲ್ಲಿ ಲಾಭ ಗಳಿಸಲು ಇಂದು ಹಣ ತೊಡಗಿಸುವುದು",
                "Commerce", "in-VEST-ment"));
        terms.add(new Term("Profit", "ಲಾಭ",
                "ಆದಾಯದಿಂದ ಎಲ್ಲ ವೆಚ್ಚ ಕಳೆದ ನಂತರ ಉಳಿಯುವ ಹಣ",
                "Commerce", "PROF-it"));

        return terms;
    }
}
