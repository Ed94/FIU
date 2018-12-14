sits_right_of(ron     , natalie ).
sits_right_of(hermione, ron     ).
sits_right_of(harry   , hermione).
sits_right_of(colin   , harry   ).
sits_right_of(seamus  , colin   ).
sits_right_of(angelina, seamus	).
sits_right_of(ginny   , angelina).
sits_right_of(dean    , ginny   ).
sits_right_of(dennis  , dean    ).
sits_right_of(lee     , dennis  ).
sits_right_of(george  , lee     ).
sits_right_of(fred    , george  ).
sits_right_of(alicia  , fred    ).
sits_right_of(neville , alicia  ).
sits_right_of(lavender, neville	).
sits_right_of(parvati , lavender).
sits_right_of(katie   , parvati ).
sits_right_of(natalie , katie   ).

%////////////////////////////////////////////////////////////////////////Rules:
sits_left_of(X, Y)   %Defines X to be sitting to the left of Y.
:-
    sits_right_of(Y, X),   %Y sits right of X.
    x \= Y             .   %X cannot be the same person as Y.

are_neighbors_of(X, Y, Z)   %Defines X, Y, Z to have a neighbor relationship.
:-
    sits_left_of( X, Z),   %X must be to the left  of Z.
    sits_right_of(Y, Z),   %Y must be to the right of Z.
    X \= Y             ,   %X cannot be the same person as Y.
    Y \= Z             .   %Y cannot be the same person as Z.

next_to_each_other(X, Y)   %Defines X to be next to Y.
:-
    sits_left_of( X, Y);   %X can be to the left  of Y.
    sits_right_of(X, Y).   %X can be to the right of Y.
% //////////////////////////////////////////////////////////////////////////////
