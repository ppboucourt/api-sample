(function () {
    'use strict';

    angular
        .module('udt')
        .directive('compareTo', compareTo);

    function compareTo() {

        var directive = {
            require: 'ngModel',
            scope: {
                passwordToCheck: '='
            },
            link: linkFunc
        };

        return directive;

        /* private helper methods*/

        function linkFunc(scope, elem, attrs, ctrl) {
            var firstPassword = '#' + attrs.compareTo;
            elem.add(firstPassword).on('keyup', function () {
                scope.$apply(function () {
                    // console.info(elem.val() === $(firstPassword).val());
                    ctrl.$setValidity('notmatch', elem.val() === $(firstPassword).val());
                });
            });
        }
    }
})();
