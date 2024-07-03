package com.salfetka.fishing.ui.wiki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.salfetka.fishing.R;
import com.salfetka.fishing.databinding.FragmentWikiBinding;

public class WikiFragment extends Fragment {

    private FragmentWikiBinding binding;

    @SuppressLint("WrongViewCast")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WikiViewModel wikiViewModel =
                new ViewModelProvider(this).get(WikiViewModel.class);

        binding = FragmentWikiBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ImageView fishingBait = root.findViewById(R.id.fishingBait);
        ImageView learnFishing = root.findViewById(R.id.learnFishing);
        ImageView fishingSecret = root.findViewById(R.id.secretFishing);
        ImageView fishingBaikal = root.findViewById(R.id.fishingBaikal);
        ImageView fishingCrap = root.findViewById(R.id.fishingCrap);

        fishingBait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://telegra.ph/Kakuyu-rybu-na-chto-lovit-06-27"));
                startActivity(browserIntent);
            }
        });
        learnFishing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://telegra.ph/Kak-nauchitsya-lovit-rybu-Sovety-nachinayushchim-rybakam-06-27"));
                startActivity(browserIntent);
            }
        });
        fishingCrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://telegra.ph/Navoz-dlya-prikormki-pochemu-rybaki-ego-ignoriruyut-06-27"));
                startActivity(browserIntent);
            }
        });
        fishingBaikal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://telegra.ph/Rybalka-na-Bajkale-Rasskazhu-chto-lovitsya-i-o-pravilah-lovle-na-ozere-06-27"));
                startActivity(browserIntent);
            }
        });
        fishingSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://telegra.ph/7-sekretov-i-hitrostej-pomogayushchie-na-rybalke-06-27"));
                startActivity(browserIntent);
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}