/**
 * Created by alien on 7/13/17.
 */

(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .directive('headerDatatable', headerDatatable);

    function headerDatatable() {
        var directive = {
            restrict: 'EA',
            transclude: true,
            templateUrl: 'app/components/header-datatable/header-datatable.tpl.html',
            // controllerAs: 'ctrl',
            bindings: {
                entity: '@',

            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, ngModel, transclude) {
            // el.append(transclude());
            // console.log("ctrl", scope);
        }

    }

})();
