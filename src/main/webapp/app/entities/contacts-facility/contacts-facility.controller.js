(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityController', ContactsFacilityController);

    ContactsFacilityController.$inject = ['$scope', '$state', 'DataUtils', 'ContactsFacility', 'ContactsFacilitySearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService'];

    function ContactsFacilityController ($scope, $state, DataUtils, ContactsFacility, ContactsFacilitySearch, $q, DTColumnBuilder,
                                         DTOptionsBuilder, GUIUtils, $filter, CoreService ) {
        var vm = this;


        vm.contactsFacilities = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        vm.title = 'Contacts';
        vm.entityClassHumanized = 'Contacts';
        vm.entityStateName = 'contacts-facility';


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var facility = CoreService.getCurrentFacility();
                ContactsFacility.contactsByFacility({id : facility.id}, function(result) {
                    vm.contactsFacilities = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.contactsFacilities, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            // DTColumnBuilder.newColumn('shortCode').withTitle('Short Code'),
            DTColumnBuilder.newColumn('typeContactFacilityType').withTitle('Contact Type').renderWith(function (data, type, full) {
                return data.name;
            }),
            DTColumnBuilder.newColumn('street').withTitle('Street'),
            DTColumnBuilder.newColumn('streetTwo').withTitle('Street Two'),
            //DTColumnBuilder.newColumn('city').withTitle('City'),
            //DTColumnBuilder.newColumn('state').withTitle('State'),
            //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
            //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
            //DTColumnBuilder.newColumn('fax').withTitle('Fax'),
            //DTColumnBuilder.newColumn('website').withTitle('Website'),
            //DTColumnBuilder.newColumn('notes').withTitle('Notes'),
            //DTColumnBuilder.newColumn('status').withTitle('Status'),
            //DTColumnBuilder.newColumn('email').withTitle('Email'),
            //DTColumnBuilder.newColumn('cellphone').withTitle('Cellphone'),
            //DTColumnBuilder.newColumn('picture').withTitle('Picture'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            var facility = CoreService.getCurrentFacility();
            ContactsFacility.contactsByFacility({id : facility.id}, onSuccessContactFacilityById, onErrorContactFacilityById);
        }

        function onSuccessContactFacilityById(result) {
            vm.contactsFacilities = result;
            vm.searchQuery = null;
        }

        function onErrorContactFacilityById(error) {
            console.log('Failed getting contacts facility: ' + error);
        }

        function actionsHtml(data){
            return '<a class="btn-sm btn-primary" ui-sref="contacts-facility.edit({id:' + data.id + '})" href="#/contacts-facility/' + data.id + '/edit">' +
                '   <i class="fa fa-eye"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ui-sref="contacts-facility.delete({id:' + data.id + '})"  href="#/contacts-facility/' + data.id + '/delete">' +
                '   <i class="fa fa-trash"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
