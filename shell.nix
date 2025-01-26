{
pkgs ? import <nixpkgs> {}
}:
pkgs.mkShell {
    nativeBuildInputs =  with pkgs; [
        openjdk17
        libGL
    ];

    shellHook = ''
        export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:${
            pkgs.lib.makeLibraryPath [
              pkgs.libGL
            ]
         };

        echo "Nix Shell for Astral Generators development"
    '';
}
