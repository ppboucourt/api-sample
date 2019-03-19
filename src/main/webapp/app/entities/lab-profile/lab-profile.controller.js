(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabProfileController', LabProfileController);

    LabProfileController.$inject = ['$scope', '$state', 'LabProfile', 'LabProfileSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function LabProfileController ($scope, $state, LabProfile, LabProfileSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.labProfiles = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        vm.title = 'Lab Profile';
        vm.entityClassHumanized = 'Lab Profile';
        vm.entityStateName = 'lab-profile';

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  LabProfile.query(function(result) {
                  vm.labProfiles = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.labProfiles, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('enabled').withTitle('Enabled'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            LabProfile.query(function(result) {
                vm.labProfiles = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" ui-sref="lab-profile-detail({id:' + data.id + '})" href="#/lab-profile/' + data.id + '">' +
                '<i class="fa fa-eye"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ui-sref="lab-profile.delete({id:' + data.id + '})"  href="#/lab-profile/' + data.id + '/delete">' +
                '<i class="fa fa-trash"></i></a>';
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
