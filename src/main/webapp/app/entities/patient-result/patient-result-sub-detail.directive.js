(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .directive('prDetails', prDetails);

    function prDetails() {
        var directive = {
            restrict: 'EA',
            transclude: true,
            templateUrl: 'app/entities/patient-result/tpl/patient-result-detail-grid.tpl.html',
            controller: ['$scope', function ($scope) {
                $scope.searchQuery = '';
                $scope.filterResult = function (res) {
                    var filterQuery = true;
                    if ($scope.searchQuery) {
                        if ((_.includes(res.testName.toLowerCase(), $scope.searchQuery.toLowerCase())) ||
                            (_.includes(res.testCode.toLowerCase(), $scope.searchQuery.toLowerCase())) ||
                            (_.includes(res.result.toLowerCase(), $scope.searchQuery.toLowerCase()))) {
                            filterQuery = true;
                        } else {
                            filterQuery = false;
                        }
                    }
                    return filterQuery;
                }
            }],
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
