(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OccupancyController', OccupancyController);

    OccupancyController.$inject = ['$scope', 'Bed', '$q', '$filter', 'CoreService', 'Chart', 'lodash', 'Building', 'loadMask', 'toastr'];

    function OccupancyController($scope, Bed, $q, $filter, CoreService, Chart, _, Building, loadMask, toastr) {
        var vm = this;

        //Variable
        vm.dragStartItem = null;
        vm.loadingBeds = true;
        vm.isOpenPopover = [];
        vm.isOverItem = [];
        vm.beds = [];
        vm.unassignedBeds = [];
        vm.unasignedPatients = [];
        vm.filterResults = [];
        vm.bedsByRoom = [];
        vm.buildings = [];
        vm.filters = {
            building: null,
            sex: '*',
            query: ''
        };

        vm.selectedBed = {
            selected: null
        }

        // methods
        vm.loadUnassignedPatients = loadUnassignedPatients;
        vm.unassignedPatientAction = unassignedPatientAction;
        vm.loadBuildings = loadBuildings;
        vm.getTemplate = getTemplate;
        vm.loadBeds = loadBeds;
        vm.changeBuilding = changeBuilding;
        vm.movePatient = movePatient;
        vm.assignPatient = assignPatient;
        vm.populateBedWithChart = populateBedWithChart;
        vm.execMovePatient = execMovePatient;
        vm.movePatientTo = movePatientTo;
        vm.closeAllPopover = closeAllPopover;
        vm.cleanBedObject = cleanBedObject;
        vm.applyFiltersRoom = applyFiltersRoom;
        vm.applyFiltersBed = applyFiltersBed;
        vm.filterPatientByRoom = filterPatientByRoom;
        vm.filterBedBySex = filterBedBySex;

        function closeAllPopover() {
            _.forEach(vm.isOpenPopover, function (room, key) {
                _.forEach(room, function (bed, index) {
                    vm.isOpenPopover[key][index] = false;
                })
            })
        }

        /**
         * Get template (with patient or without)
         * @param bed
         * @returns {string}
         */
        function getTemplate(bed) {
            return (bed.chartId) ? 'bedWithPatientPopover.html' : 'bedWithoutPatientPopover.html';
        }

        function cleanBedObject(bed) {
            //clean bed obj
            delete bed.patientFullName;
            delete bed.patientPicture;
            delete bed.patientPictureContentType;
            delete bed.patientMrNo;
        }

        function populateBedWithChart(bed, chart) {
            //Exchange chartId
            bed.chartId = chart.id;
            bed.patientFullName = chart.patient.firstName + ' ' + chart.patient.lastName;
            bed.patientPicture = chart.pictureRef.picture;
            bed.patientPictureContentType = chart.pictureRef.pictureContentType;
            bed.patientMrNo = chart.mrNo;
        }

        function assignPatient(patient) {
            if (patient && patient.id && vm.selectedBed.selected) {
                var bedToAssign = _.clone(vm.selectedBed.selected, true);
                bedToAssign.chartId = patient.id;
                Bed.update(bedToAssign).$promise
                    .then(function (result) {
                        toastr.success('The patient was assigned successfully to the bed [' + vm.selectedBed.selected.name + '].');
                        vm.populateBedWithChart(vm.selectedBed.selected, patient);
                        //Remove the patient from unassigned list
                        _.remove(vm.unassignedPatients, function (row) {
                            return row.id === patient.id;
                        })
                        //Filter the unassigned bed again
                        preparingBedResult(vm.bedsByRoom, true);
                        filterPatient(vm.allPatients);
                    })
                    .catch(function (error) {
                        console.log("error", error);
                    })
                    .finally(function () {
                        closeAllPopover();
                    });
            } else {
                toastr.info("You have to select a patient first to assign to this bed.");
            }
        }

        function unassignedPatientAction() {
            // console.log("vm.selectedBed.selected", vm.selectedBed.selected);
            if (vm.selectedBed.selected) {
                var bedToUnassigned = _.clone(vm.selectedBed.selected, true);
                vm.cleanBedObject(bedToUnassigned);
                bedToUnassigned.chartId = null;
                Bed.update(bedToUnassigned).$promise
                    .then(function (result) {
                        toastr.success('The patient was moved out successfully.');
                        vm.selectedBed.selected.chartId = null;
                        //Filter the unassigned bed again
                        vm.cleanBedObject(vm.selectedBed.selected);
                        preparingBedResult(vm.bedsByRoom, true);
                        // filterPatient(vm.allPatients);
                        vm.loadUnassignedPatients();
                    })
                    .catch(function (error) {
                        console.log("error", error);
                    })
                    .finally(function () {
                        closeAllPopover();
                    });
            }

        }

        function loadBuildings() {
            Building.byfacility({id: CoreService.getCurrentFacility().id}, function (result) {
                vm.buildings = result;
                vm.filters.building = result[0];
                vm.loadBeds(vm.filters.building);
            });
        }

        vm.loadBuildings();

        function loadBeds(building) {
            var defer = $q.defer();
            // var facility = CoreService.getCurrentFacility();
            Bed.byBuilding({id: building.id}, function (result) {
                vm.beds = result;
                preparingBedResult(result);
                // vm.bedsByRoom = _.chain(result).groupBy('room.name').value();
                vm.bedsByRoom = _.values(_.groupBy(result, 'room.name'));
                vm.loadingBeds = false;
                // console.log("bedByRoom", vm.bedsByRoom);
                vm.loadUnassignedPatients();
                defer.resolve(result);
            });
            return defer.promise;
        }

        function loadUnassignedPatients() {
            var facility = CoreService.getCurrentFacility();
            Chart.unassignedBed({id: facility.id}, function (result) {
                preparingChartResult(result);
                vm.allPatients = _.clone(_.filter(result, function (row) {
                    return !row.bed;
                }), true);

                filterPatient(vm.allPatients);
            });
        }

        function filterPatientByRoom() {
            return function (item) {
                var selBed = vm.selectedBed.selected;
                if (item.patient.sex.toLowerCase() !== selBed.room.sex.toLowerCase()) {
                    return false;
                }
                return true;
            };
        }

        function filterBedBySex() {
            return function (item) {
                var selBed = vm.selectedBed.selected;
                if (item.room.sex.toLowerCase() !== selBed.room.sex.toLowerCase()) {
                    return false;
                }
                return true;
            };
        }

        function filterPatient(result) {
            vm.unassignedPatients = _.filter(result, function (row) {
                var checkExistPatient = _.find(_.flatMap(vm.bedsByRoom), function (bed) {
                    return bed.chartId === row.id;
                })
                return !checkExistPatient;
            });
        }

        /**
         * Filter via Room
         * @param room
         * @returns {boolean}
         */
        function applyFiltersRoom(room) {
            var firstBed = (room) ? room[0] : null;
            var filterQuery = true;
            var filterSex = true;
            //Sex filter
            if (firstBed && firstBed.room.sex !== vm.filters.sex && vm.filters.sex !== '*') {
                filterSex = false;
            }
            if (vm.filters.query) {
                filterQuery = _.find(room, function (bed, index) {
                    //Check that the query sought this included bed
                    if (_.includes(bed.name.toLowerCase(), vm.filters.query.toLowerCase()) ||
                        (bed.patientFullName && _.includes(bed.patientFullName.toLowerCase(), vm.filters.query.toLowerCase()))) {
                        return true;
                    }
                });
            }

            return (filterSex && filterQuery);
        }

        /**
         * Filter via Bed
         * @param bed
         * @returns {boolean}
         */
        function applyFiltersBed(bed) {
            var filterQuery = true;
            if (vm.filters.query) {
                if (!_.includes(bed.name.toLowerCase(), vm.filters.query.toLowerCase()) &&
                    !(bed.patientFullName && _.includes(bed.patientFullName.toLowerCase(), vm.filters.query.toLowerCase()))) {
                    filterQuery = false;
                }
            }
            return filterQuery;
        }

        function changeBuilding() {
            vm.loadingBeds = true;
            vm.bedsByRoom = [];
            vm.loadBeds(vm.filters.building);
        }

        vm.dragOverCallback = function (index, key, external, type, callback) {
            // console.log("type ", type, index, key, external);
            // Invoke callback to origin for container types.
            if (type == 'patient' && !external) {
                // console.log('Container being dragged contains items');
            }
            return index >= 0;
        };

        /**
         *
         * @param index - bed index in the room
         * @param key - room index
         * @param item -
         * @param external
         * @param type
         * @returns {boolean}
         */
        vm.dropCallback = function (index, key, item, external, type) {
            var targetBed = vm.bedsByRoom[key][index];
            var selector = index + key;
            var sourceItem = vm.dragStartItem;
            // console.log('source item', sourceItem.id);
            // console.log('dropped at item', targetBed.id);
            if (targetBed.id !== sourceItem.id && sourceItem.chartId) {
                //Check gender
                if (sourceItem.room.sex === targetBed.room.sex) {
                    //Check if in the dropped Item exist a patient
                    if (targetBed.chartId !== null) {
                        toastr.warning('Already exist a patient in this bed [' + targetBed.name + ']');
                    } else {
                        //Show loading message
                        loadMask.create('lm_bed_' + selector, "Assigning patient <span class='hidden-xs hidden-sm hidden-md'> to [" + targetBed.name + ']</span>', '#bed_' + selector);
                        loadMask.show('#lm_bed_' + selector);
                        vm.execMovePatient(targetBed, sourceItem, selector);
                    }
                } else {
                    var sexSource = $filter('capitalize')(sourceItem.room.sex);
                    var sexTarget = $filter('capitalize')(sourceItem.room.sex);
                    toastr.warning("You can't assign a " + sexSource + " Patient in a " + sexTarget + " Room.");
                }
            }

        };

        /**
         * Execute move patient (drag and drop or button move to)
         * @param targetBed
         * @param sourceItem
         * @param selector
         */
        function execMovePatient(targetBed, sourceItem, selector) {
            vm.movePatient(targetBed, sourceItem)
                .then(function (result) {
                    if (result) {// && result[0] && result[1]
                        toastr.success('The patient was moved successfully.');
                        //Exchange chartId
                        targetBed.chartId = sourceItem.chartId;
                        targetBed.patientFullName = sourceItem.patientFullName;
                        targetBed.patientPicture = sourceItem.patientPicture;
                        targetBed.patientPictureContentType = sourceItem.patientPictureContentType;
                        targetBed.patientMrNo = sourceItem.patientMrNo;
                        //Clean old bed
                        sourceItem.chartId = null;
                        vm.cleanBedObject(sourceItem);
                        //Filter the unassigned bed again
                        preparingBedResult(vm.bedsByRoom, true);

                    }
                })
                .catch(function (error) {
                    toastr.error('Error', 'Error ...');
                    console.log("error", error);
                })
                .finally(function () {
                    closeAllPopover();
                    loadMask.hide('#lm_bed_' + selector);
                    // Return false here to cancel drop. Return true if you insert the item yourself.
                    return false;
                })
        }

        function movePatientTo(bed) {
            //Find the real bed in the array
            if (bed && bed.id) {
                var realBed = null;
                _.forEach(vm.bedsByRoom, function (room) {
                    _.forEach(room, function (b) {
                        if (b.id === bed.id) {
                            realBed = b;
                        }
                    })
                })

                if (vm.selectedBed.selected && realBed) {
                    //Check gender
                    if (realBed.room.sex === vm.selectedBed.selected.room.sex) {
                        vm.execMovePatient(realBed, vm.selectedBed.selected);
                    } else {
                        var sexSource = $filter('capitalize')(realBed.room.sex);
                        var sexTarget = $filter('capitalize')(vm.selectedBed.selected.room.sex);
                        toastr.warning("You can't assign a " + sexSource + " Patient in a " + sexTarget + " Room.");
                    }
                }
            } else {
                toastr.info("You have to select a bed first to move a patient.");
            }
        }


        function movePatient(targetBed, sourceItem) {
            var allPromises = [];
            if (sourceItem && targetBed) {
                //Unassigned bed
                var bedToUnassigned = _.clone(sourceItem, true);
                vm.cleanBedObject(bedToUnassigned);
                bedToUnassigned.chartId = null;
                allPromises.push(Bed.update(bedToUnassigned).$promise);

                //Assign bed
                var bedToAssigned = _.clone(targetBed, true);
                bedToAssigned.chartId = sourceItem.chartId;
                vm.cleanBedObject(bedToAssigned);
                allPromises.push(Bed.update(bedToAssigned).$promise);
            }
            return $q.all(allPromises);
        }

        vm.logEvent = function (message) {
            console.log(message);
        };

        vm.logListEvent = function (action, index, external, type) {
            // var message = external ? 'External ' : '';
            // message += type + ' element was ' + action + ' position ' + index;
            // console.log(message);
        };

        function preparingBedResult(data, flatMap) {
            var dataToFilter = (!flatMap) ? data : _.flatMap(data);
            vm.unassignedBeds = _.filter(dataToFilter, function (row) {
                return row.chartId == null;
            });
        }

        function preparingChartResult(data) {
            for (var i = 0; i < data.length; i++) {
                data[i].patient.fullName = data[i].patient.firstName + ' ' + data[i].patient.lastName;
            }
        }

    }
})();
