(function () {

    var cv = angular.module('cv');

    cv.config([ '$translateProvider', function ($translateProvider) {

        $translateProvider.translations('en', {
            'A': 'a',
            'AL': 'Alytus',
            'B': 'b',
            'C': 'c',
            'CHANGE LANGUAGE': 'Change language',
            'CLOSE': 'Close',
            'CITY': 'City',
            'D': 'd',
            'E': 'e',
            'EMAIL': 'Email',
            'F': 'f',
            'G': 'g',
            'H': 'h',
            'I': 'i',
            'J': 'j',
            'K': 'k',
            'KN': 'Kaunas',
            'KLP': 'Klaipeda',
            'L': 'l',
            'LOGIN': 'Log in',
            'LOG IN WITH YOUR EMAIL ADDRESS': 'Log in with your email address',
            'M': 'm',
            'MR': 'Marijampole',
            'MZK': 'Mazeikiai',
            'N': 'n',
            'O': 'o',
            'P': 'p',
            'PASSWORD': 'Password',
            'PNV': 'Panevezys',
            'Q': 'q',
            'R': 'r',
            'S': 's',
            'SL': 'Siauliai',
            'T': 't',
            'THRESHOLD': 'Threshold',
            'U': 'u',
            'V': 'v',
            'VLN': 'Vilnius',
            'W': 'w',
            'WRONG CREDENTIALS': 'wrong credentials',
            'X': 'x',
            'Y': 'y',
            'Z': 'z'
        });

        $translateProvider.translations('lt', {
            'A': 'a',
            'AL': 'Alytus',
            'B': 'b',
            'C': 'c',
            'CHANGE LANGUAGE': 'Pakeisti kalbą',
            'CLOSE': 'Close',
            'CITY': 'City',
            'D': 'd',
            'E': 'e',
            'EMAIL': 'Email',
            'F': 'f',
            'G': 'g',
            'H': 'h',
            'I': 'i',
            'J': 'j',
            'K': 'k',
            'KN': 'Kaunas',
            'KLP': 'Klaipeda',
            'L': 'l',
            'LOGIN': 'Prisijungti',
            'LOG IN WITH YOUR EMAIL ADDRESS': 'Log in with your email address',
            'M': 'm',
            'MR': 'Marijampole',
            'MZK': 'Mazeikiai',
            'N': 'n',
            'O': 'o',
            'P': 'p',
            'PASSWORD': 'Password',
            'PNV': 'Panevezys',
            'Q': 'q',
            'R': 'r',
            'S': 's',
            'SL': 'Siauliai',
            'T': 't',
            'THRESHOLD': 'Žemiausia riba',
            'U': 'u',
            'V': 'v',
            'VLN': 'Vilnius',
            'W': 'w',
            'WRONG CREDENTIALS': 'blogi prisijungimo duomenys',
            'X': 'x',
            'Y': 'y',
            'Z': 'z'
        });

        $translateProvider.preferredLanguage('en');

    } ]);

}());