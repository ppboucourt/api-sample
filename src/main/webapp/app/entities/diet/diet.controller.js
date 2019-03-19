(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DietController', DietController);

    DietController.$inject = ['$compile', '$scope', 'chart', 'DietSearch', '$q', 'DTColumnBuilder', 'DTOptionsBuilder',
        '$stateParams', '$filter', '$uibModal', 'Diet'];

    function DietController($compile, $scope, chart, DietSearch, $q, DTColumnBuilder, DTOptionsBuilder, $stateParams,
                            $filter, $uibModal, Diet) {
        var vm = this;

        vm.diets = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.chart = chart;

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var must = [];
                must.push({term: {"chartId": vm.chart.id}});
                must.push({term: {"delStatus": false}});

                DietSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.diets = result;
                    $scope.$emit('bluebookApp:dietsCount', {
                        total: result.length
                    });
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.diets, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('description').withTitle('Description'),
            DTColumnBuilder.newColumn('typeFoodDiets').withTitle('Type Food Diets').renderWith(function (data, type, full) {
                return data ? data.name : 'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.saveDiet(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a> ' +
                ' <a class="btn-sm btn-danger" ng-click="vm.deleteDiet(' + data.id + ')">' +
                '<i class="fa fa-trash"></i></a> ';
        }

        vm.saveDiet = function (id) {
            var entity = null;
            //Edit the entity
            if (id) {
                entity = Diet.get({id: id}).$promise;
            } else {
                entity = {
                    description: null,
                    status: 'ACT',
                    id: null,
                    chartId: $stateParams.id
                };
            }

            $uibModal.open({
                templateUrl: 'app/entities/diet/diet-dialog.html',
                controller: 'DietDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return entity;
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function (error) {
                console.log("error", error);
                //Error handling
            });
        }

        vm.deleteDiet = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/diet/diet-delete-dialog.html',
                controller: 'DietDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    entity: {
                        id: id
                    }
                }
            }).result.then(function () {
                vm.search();
            }, function () {
                //Error
            });
        }


        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
