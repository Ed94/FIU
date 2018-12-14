%Problem 4-Part B----------------------------------------Facts:
%
%--------------------------------Car:
by_Car(miami        , orlando      ).
by_Car(orlando      , tampa        ).
by_Car(philadelphia , washington_dc).
by_Car(philadelphia , new_york     ).
%////////////////////////////////////
%
%---------------------------Train:
by_Train(new_york     , atlanta ).
by_Train(washington_dc, atlanta ).
by_Train(new_york     , montreal).
by_Train(washington_dc, montreal).
%/////////////////////////////////
%
%--------------------------Plane:
by_Plane(atlanta   , bangkok   ).
by_Plane(atlanta   , singapore ).
by_Plane(montreal  , losAngeles).
by_Plane(bangkok   , miami     ).
by_Plane(losAngeles, miami     ).
%////////////////////////////////
%
%/////////////////////////////////////////////////////////////
%
% -------------------------------------------------------Rules:
travel_via_vehicle(Origin, Destination)   %Defines a valid mode of transport between locations.
:-
    by_Car(  Origin, Destination);   %Can be by car.
    by_Train(Origin, Destination);   %Can be by train.
    by_Plane(Origin, Destination).   %Can be by plane.


travel(Origin, Destination)   %Defines a valid path to travel from one location to another.
:-                            %Bahaves as base case.
    %Can be done using a single vehicle.
    travel_via_vehicle(Origin, Destination),

    Origin \= Destination   %Origin cannot be the destination.
.

travel(Origin, Destination)   %Can chain multiple spokes to the destination.
:-
    travel_via_vehicle(Origin, Spoke      ),   %Travel to one spoke.
    travel(            Spoke , Destination)    %Spoke can be chained from here on out.
.

% /////////////////////////////////////////////////////////////
