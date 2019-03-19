(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeLevelCareController', TypeLevelCareController);

    TypeLevelCareController.$inject = ['$scope', '$state', 'TypeLevelCare', 'TypeLevelCareSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService'];

    function TypeLevelCareController ($scope, $state, TypeLevelCare, TypeLevelCareSearch, $q, DTColumnBuilder,
                                      DTOptionsBuilder, GUIUtils, $filter, CoreService ) {
        var vm = this;


        vm.typeLevelCares = [];
        vm.search = search;
        // vm.loadAll = loadAll;
        vm.dtInstance = {};


        // loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {

                var must = [];
                must.push({term: {"facility.id": CoreService.getCurrentFacility().id}});
                must.push({term: {"delStatus": false}});

                TypeLevelCareSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.typeLevelCares = result;
                    defer.resolve(result);
                });
                // TypeLevelCare.query(function(result) {
                //     vm.typeLevelCares = result;
                //     defer.resolve(result);
                // });
            }else {
                defer.resolve($filter('filter')(vm.typeLevelCares, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        // function loadAll() {
        //     TypeLevelCare.query(function(result) {
        //         vm.typeLevelCares = result;
        //         vm.searchQuery = null;
        //     });
        // }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
