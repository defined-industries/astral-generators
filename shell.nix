{
pkgs ? import <nixpkgs> {}
}:
pkgs.mkShell {
    nativeBuildInputs =  with pkgs; [
        openjdk17
        libgl
        pipewire
    ];

    shellHook = ''
        export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:${
            pkgs.lib.makeLibraryPath [
              libgl
              pipewire
            ]
         };

        echo "Nix Shell for Astral Generators development"
    '';
}
