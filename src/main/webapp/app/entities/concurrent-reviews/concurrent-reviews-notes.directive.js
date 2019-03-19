(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .directive('crNotes', crNotes);

    function crNotes() {
        var directive = {
            restrict: 'EA',
            transclude: true,
            templateUrl: 'app/entities/concurrent-reviews/concurrent-review-notes.tpl.html',
            // controllerAs: 'ctrl',
            bindings: {
                data: '@',

            },
            link: linkFunc
        };

        return directive;

        function linkFunc(scope, element, attrs, ngModel, transclude) {
            // console.log("scopeeee", scope);
        }

    }

})();
