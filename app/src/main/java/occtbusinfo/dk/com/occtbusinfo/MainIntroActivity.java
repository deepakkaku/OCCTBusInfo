package occtbusinfo.dk.com.occtbusinfo;

import android.content.Intent;
import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by Dk on 14-04-2016.
 */
public class MainIntroActivity extends IntroActivity {
    public static final String EXTRA_FULLSCREEN = "com.heinrichreimersoftware.materialintro.demo.EXTRA_FULLSCREEN";

    public static final String EXTRA_SCROLLABLE = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SCROLLABLE";

    public static final String EXTRA_CUSTOM_FRAGMENTS = "com.heinrichreimersoftware.materialintro.demo.EXTRA_CUSTOM_FRAGMENTS";

    public static final String EXTRA_PERMISSIONS = "com.heinrichreimersoftware.materialintro.demo.EXTRA_PERMISSIONS";

    public static final String EXTRA_SKIP_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_SKIP_ENABLED";

    public static final String EXTRA_FINISH_ENABLED = "com.heinrichreimersoftware.materialintro.demo.EXTRA_FINISH_ENABLED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();

        boolean fullscreen = intent.getBooleanExtra(EXTRA_FULLSCREEN, false);
        boolean scrollable = intent.getBooleanExtra(EXTRA_SCROLLABLE, false);
        boolean customFragments = intent.getBooleanExtra(EXTRA_CUSTOM_FRAGMENTS, true);
        boolean permissions = intent.getBooleanExtra(EXTRA_PERMISSIONS, true);
        boolean skipEnabled = intent.getBooleanExtra(EXTRA_SKIP_ENABLED, true);
        boolean finishEnabled = intent.getBooleanExtra(EXTRA_FINISH_ENABLED, true);

        setFullscreen(fullscreen);

        super.onCreate(savedInstanceState);

        setSkipEnabled(skipEnabled);
        setFinishEnabled(finishEnabled);

        addSlide(new SimpleSlide.Builder()
                .title("Welcome")
                .description("to OCCT Bus Info app.")
                .image(R.drawable.slide_1)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .scrollable(scrollable)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Dont Miss it!")
                .description("Don't waste time searching PDFs.\nFind Your Next bus in 10 secs.")
                .image(R.drawable.slide_2)
                .background(R.color.color_material_bold)
                .backgroundDark(R.color.color_dark_material_bold)
                .scrollable(scrollable)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("It's Simple")
                .description("1. Select your bus route.\n2. Select your location.\n3. BOOM! There you have it. \nIsn't that easy?")
                .image(R.drawable.icon_comfortable)
                .background(R.color.color_material_motion)
                .backgroundDark(R.color.color_dark_material_motion)
                .scrollable(scrollable)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Let's GO")
                .description("Let's get started and never miss a bus again. \nHave fun!")
                .image(R.drawable.slide_last)
                .background(R.color.color_material_metaphor)
                .backgroundDark(R.color.color_dark_material_metaphor)
                .scrollable(scrollable)
                .build());

    }
}
